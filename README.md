# TrendHub – Trending Repositories for Android

TrendHub is an Android app that displays trending GitHub repositories.
The app is built using Jetpack Compose, Kotlin Coroutines, and follows a state-driven architecture
to ensure a responsive and predictable user experience.

---

## Overview

TrendHub fetches trending repositories directly from the GitHub API and presents them in a modern,
intuitive interface.
The app manually handles pagination while maintaining a single source of truth for state.
State updates are managed within the ViewModel, ensuring clear separation of concerns and a
consistent UI state.

---

## Key Features

- **Fetch Trending Repositories:** Retrieve data from the GitHub API.
- **Manual Pagination:** Custom pagination implementation instead of Paging 3 for more predictable
  state management.
- **Modern UI:** User interface built with Jetpack Compose.
- **Robust Architecture:**
    - **ViewModel:** Manages the screen state and user actions.
    - **UseCase:** Encapsulates business logic.
    - **Repository:** Handles API requests and data fetching.

---

## Project Structure

*Note:* The project structure is simplified to avoid unnecessary complexity in a small project.

---

## Tech Stack

- **Kotlin** – Primary programming language.
- **Jetpack Compose** – Declarative UI toolkit.
- **Coroutines & Flow** – For asynchronous operations.
- **Hilt** – Dependency injection framework.
- **Retrofit** – HTTP client for network requests.
- **Coil** – Image loading library.

---

## API Integration

The app uses GitHub API:

- **Fetch Trending Repositories:**
  ```https
  GET https://api.github.com/search/repositories?q=language:Kotlin&sort=stars&order=desc

- **Fetch Trending Repositories:**
  ```https
  GET https://api.github.com/repos/{owner}/{repo}

## Getting Started

- **Android Studio Giraffe** or newer.
- **JDK 17+**
- A device or emulator running **Android 8.0 (API 26)** or higher.

### Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/marin-hen/TrendHub.git
    ```
2. **Build the project:**
    ```sh
    ./gradlew assembleDebug
    ```
3. **Run the app:**
    - Use an emulator or a physical Android device to run the app through Android Studio.

### Future Improvements

- **Filters & Search:** Enhance repository results with refined search options.
- **UI Testing:** Increase test coverage to ensure a smooth user experience.
- **Offline Mode:** Implement data caching to improve usability when offline.

