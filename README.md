# iTunes Search App

This app demonstrates the *search by term* API from iTunes Web Services.

[Download APK installer](https://github.com/rhychel/iTunesSearch/raw/master/iTunesSearch.apk)

### Features

1. Search by term
2. Tap the result to see the long description of the track
3. Shows last visit to the app
4. The results are based on the `Locale.getInstance().country` country code.

### Data Persistence

I used **Room Database** in saving some of the data locally. It saves the following:

 1. Last visit to the app
 2. Last searched track results (it clears the table everytime new data is fetched)

### App Architecture

The project uses **Clean Architecture** with **repository pattern** to persist data and access the APIs.

#### ---- Domain ----
The domain layer establish the functionality of the application based on how the user expects it to behave.

 - SearchByTermUseCase - This calls the API for search then persist the results. When no results are found, it throws a `NoDataException`.
 - SaveSessionUseCase - This saves the last visit to the app in the database as a formatted date/time.
 - GetLastSessionUseCase - This retrieves the last visit from the database. It throws `NoDataException` during the first run of the app.
 - GetLastSearchedTracksUseCase - This retrieves the last searched track results from the database. It throws `NoDataException` when nothing is saved.

#### ---- Data ----
The data layer is separated by **DB** and **Remote** which opens the possibility of plugging in a different DB or different API integrations with the same result without touching the functionality of domain by just mapping the models of DB and RAW to the Domain model.

#### ---- Gateway ----
`SearchGateway` integrates the domain business logics to the data layer. It is implemented in `SearchGatewayImpl` injecting the instances of `SearchLocalService` and `SearchRemoteService`.

### MVP
The app uses the Model-View-Presenter pattern in implementing the presentation layer of the architecture.

### Unit Tests
The project has unit tests available for the use cases and the presenter itself.