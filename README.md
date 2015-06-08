## Środowisko do integrowania danych lotniczych oraz pogodowych, udostępniające API i umożliwiające dalszą analizę danych

### 1. Opis zawartości archiwum
* documentation - folder zawiera dokumentację w wersji edytowalnej (*documentation.docx*) oraz finalnek (*documentation.pdf*)
* presentation - folder zawiera prezentację z zajęć w wersji edytowalnej (*presentation.pptx*) oraz finalnej (*presentation.pdf*)
* source_code - folder z kodem źródłowym aplikacji
* database.zip - spakowana baza danych

### 2. Wymagania systemu
* java - wersja przynajmniej 1.8.0_45
* dostęp do internetu

### 3. Sposób uruchamiania
Aby uruchomić aplikację, w folderze *source_code* należy wykonać komendę *./gradlew bootRun*. Spowoduje to uruchomienie aplikacji, która zarejestruje joby pobierające dane z serwisów i jednocześnie API pozwalające na przeglądanie aktualnej zawartości bazy danych.

3.1 Istnieje możliwość kontynuowania pobierania danych dostarczonych w pliku *database.zip*. Należy rozpakować jego zawartość do katalogy *source_code* i uruchomić aplikację normalnie.

### 4. Przeglądanie zawartości bazy danych
Aby przeglądać zawartość bazy danych można wykorzytać aplikację - wówczas należy postąpić jak w punkcie 3.1.

Istnieje możliwość przeglądania zawartości bazy danych podłączając się do niej przy użyciu samodzielnej instalacji neo4j. W tym celu należy ściągnąć i zainstalować neo4j ze [neo4j](http://neo4j.com), a następnie, podczas uruchamiania neo4j, ustawić database.zip/data jako folder z bazą danych.
