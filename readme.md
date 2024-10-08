# StockEx

StockEx is a comprehensive stock market application that provides real-time stock information, portfolio tracking, and detailed stock analysis.

## Video Demo


## Features

### Splash Screen
- Animated stock price movement effect

### Home Page
- Display of top gainers and losers
- Stock details including price, price change, and percentage change
- Quick access to detailed stock information
- Search functionality
- Current portfolio overview with daily profit/loss tracking

### Search Page
- Search by company name or stock symbol
- Auto-suggestions for related stocks
- Recent search history
- Direct access to stock details

### Detail Page
- Comprehensive stock information:
    - Current price
    - Daily price change
    - Percentage change
    - Company description
    - Market capitalization
    - P/E ratio
    - Dividend yield
    - EPS
    - Book value
    - Revenue
    - Net company income
- Interactive line graph with multiple timeframes:
    - Day
    - Week
    - Month
    - 1 Year
    - 5 Years
    - All (20 years)
- Point-specific data display on graph interaction
- Expandable animated description card

### Additional Features
- `Day and night mode UI`
- Comprehensive error handling with snackbar notifications
- `Network connection issue notifications`

## Technologies Used

- Kotlin
- Jetpack Compose

### Dependencies
- Hilt (Dependency Injection)
- Coroutines
- Flows
- Vico (for graphs)
- Retrofit
- OkHttp
- Coil (Image loading)
- Material Design library
- Jetpack Navigation
- Kotlinx Serialization

## Architecture

The project follows MVVM (Model-View-ViewModel) and Clean Architecture principles, implemented with Material 3 Design components.

## Note

While this project is implemented in Kotlin with Jetpack Compose, I am also proficient in creating similar applications using XML layouts, React Native, and Flutter.

## Installation

- Clone the repository and open the project in Android Studio.

```bash
git clone https://github.com/itssinghankit/Stockex.git
```

- Download the required dependencies and run the project(done automatically after cloning).

- Add the following lines in `local.properties` file present in root directory if not present make one with same file name `local.properties`.
```bash
RAPID_API_KEY=af030ddc2bmshc517530c7441ee7p1fd45djsnb7961db8453c
ALPHA_VANTAGE_API_KEY=EPD1LFPQGZ620FE5
RAPID_API_HOST=alpha-vantage.p.rapidapi.com
BASE_URL=https://alpha-vantage.p.rapidapi.com/
ALPHA_VANTAGE_BASE_URL=https://www.alphavantage.co/
```

- These Api are free version of the actual api so they have some limitations. In case you you see error of "API limit exceeded" then you can create your own API keys by visiting to change the api key in `local.properties` file.
    1. [Rapid API](https://rapidapi.com/alphavantage/api/alpha-vantage)
    2. [Alpha Vantage](https://www.alphavantage.co/support/#api-key)

## License

Ankit Singh (itssinghankit)