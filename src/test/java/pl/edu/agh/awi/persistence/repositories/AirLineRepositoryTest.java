package pl.edu.agh.awi.persistence.repositories;


import pl.edu.agh.awi.persistence.model.AirLine;


public class AirLineRepositoryTest extends AbstractAviationGraphRepositoryTest<AirLine> {


    @Override
    protected AviationDelegate<AirLine> createAviationDelegate() {
        AirLine airLine = new AirLine();
        return AviationDelegate.build()
                .withAviationItem(airLine)
                .withNameSetter(airLine::setName)
                .withIcaoCodeSetter(airLine::setIcaoCode)
                .withIataCodeSetter(airLine::setIataCode);
    }

    @Override
    protected AviationGettersComposite createGettersCompositeFor(AirLine aviationItem) {
        return AviationGettersComposite.build()
                .withNameGetter(aviationItem::getName)
                .withIcaoCodeGetter(aviationItem::getIcaoCode)
                .withIataCodeGetter(aviationItem::getIataCode);
    }


}
