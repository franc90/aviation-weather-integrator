package pl.edu.agh.awi.persistence.repositories;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
@Transactional
public abstract class AbstractAviationGraphRepositoryTest<T> {

    private Map<String, Function<String, T>> findMethodMap;
    private static final String NAME = "NAME";
    private static final String ICAO = "ICAO";
    private static final String IATA = "IATA";

    @Autowired
    private AviationGraphRepository<T> repository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    protected abstract AviationDelegate<T> createAviationDelegate();
    protected abstract AviationGettersComposite createGettersCompositeFor(T aviationItem);


    @Before
    @Transactional
    public void initDatabase(){
        initFindMethodMap();
        saveToDatabase();
    }

    private void initFindMethodMap() {
        findMethodMap = Maps.newHashMap();
        findMethodMap.put(NAME, repository::findByName);
        findMethodMap.put(ICAO, repository::findByIcaoCode);
        findMethodMap.put(IATA, repository::findByIataCode);
    }

    private void saveToDatabase() {
        AviationDelegate<T> delegate = createAviationDelegate();
        T airLine = delegate.aviationItem;
        delegate.nameSetter.accept(NAME);
        delegate.icaoCodeSetter.accept(ICAO);
        delegate.iataCodeSetter.accept(IATA);
        neo4jTemplate.saveOnly(airLine);
    }

    @Test
    public void shouldFindByName() {
        assertFindBy(NAME);
    }

    @Test
    public void shouldFindByIcaoCode() {
        assertFindBy(ICAO);
    }


    @Test
    public void shouldFindByIataCode() {
        assertFindBy(IATA);
    }

    private void assertFindBy(String key) {
        T aviationItem = findMethodMap.get(key).apply(key);
        assertNotNull(aviationItem);
        AviationGettersComposite gettersComposite = createGettersCompositeFor(aviationItem);
        Supplier<String> getter = gettersComposite.getGetterMethod(key);
        assertEquals(key, getter.get());
    }

    static class AviationDelegate<T> {

        private final T aviationItem;
        private final Consumer<String> nameSetter;
        private final Consumer<String> icaoCodeSetter;
        private final Consumer<String> iataCodeSetter;

        static <T> AviationDelegateBuilder<T> build() {
            return aviationItem -> nameSetter -> icaoCodeSetter -> iataCodeSetter ->
                    new AviationDelegate<>(aviationItem, nameSetter, icaoCodeSetter, iataCodeSetter);
        }

        private AviationDelegate(T aviationItem, Consumer<String> nameSetter, Consumer<String> icaoCodeSetter, Consumer<String> iataCodeSetter) {
            this.aviationItem = aviationItem;
            this.nameSetter = nameSetter;
            this.icaoCodeSetter = icaoCodeSetter;
            this.iataCodeSetter = iataCodeSetter;
        }

        interface AviationDelegateBuilder<T> {
            NameSetterFunction withAviationItem(T aviationItem);
        }
        interface NameSetterFunction {
            IcaoCodeSetterFunction withNameSetter(Consumer<String> nameSetter);
        }

        interface IcaoCodeSetterFunction {
            IataCodeFunction withIcaoCodeSetter(Consumer<String> icaoCodeSetter);
        }

        interface IataCodeFunction {
            AviationDelegate withIataCodeSetter(Consumer<String> iataCodeSetter);
        }

    }

    static class AviationGettersComposite {

        private final Map<String, Supplier<String>> getters = Maps.newHashMap();

        static AviationGettersCompositeBuilder build() {
            return nameGetter -> icaoCodeGetter -> iataCodeGetter ->
                    new AviationGettersComposite(nameGetter, icaoCodeGetter, iataCodeGetter);
        }

        private AviationGettersComposite(Supplier<String> nameGetter, Supplier<String> icaoCodeGetter, Supplier<String> iataCodeGetter) {
            getters.put(NAME, nameGetter);
            getters.put(IATA, iataCodeGetter);
            getters.put(ICAO, icaoCodeGetter);
        }

        Supplier<String> getGetterMethod(String key) {
            return getters.get(key);
        }

        interface AviationGettersCompositeBuilder {
            IcaoCodeGetterFunction withNameGetter(Supplier<String> nameGetter);
        }
        interface IcaoCodeGetterFunction {
            IataCodeGetterFunction withIcaoCodeGetter(Supplier<String> icaoCodeGetter);
        }

        interface IataCodeGetterFunction {
            AviationGettersComposite withIataCodeGetter(Supplier<String> iataCodeGetter);
        }
    }

}
