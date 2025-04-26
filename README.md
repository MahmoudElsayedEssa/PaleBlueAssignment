
# 📸 Pixabay Image Search App
> A modern Android app using Jetpack Compose + MVVM + Paging 3, with local caching via Room Database and Coil.  
> Search and browse images from the [Pixabay API](https://pixabay.com/api/) with a beautiful UI and offline support.

## ✨ Features
- 🔍 **Search** images by keyword with elegant UI
- 🖼️ **Lazy loading** with **Paging 3** and shimmer effects
- 🚀 **Offline-first** with Room database caching
- 💫 **Shared element transitions** between grid and detail views
- ❤️ **Details screen** for image info (likes, downloads, comments, etc.)
- ⚡ Built using modern Android libraries and best practices
- 🎨 Powered by **Jetpack Compose** with Material 3 design

---
## 📱 Screenshots
https://github.com/user-attachments/assets/73b561b9-8a3c-4294-9c4a-474470d79e18

---
## 🛠️ Tech Stack
| Layer           | Libraries |
|-----------------|-----------|
| UI              | Jetpack Compose, Material 3, Shared Transitions, Haze |
| Architecture    | MVVM, Repository Pattern, Clean Architecture |
| Networking      | Retrofit, Kotlinx Serialization |
| Database        | Room, Paging 3 |
| DI              | Hilt |
| Image Loading   | Coil |
| Coroutines      | Kotlin Coroutines, Flow |

---
## 📦 Project Structure
```
├── data/
│   ├── remote/      # Retrofit services, interceptors, DTOs
│   │   ├── api/     # Pixabay API service
│   │   ├── paging/  # Remote mediator for Paging 3
│   │   └── mappers/ # DTO to domain mappers
│   ├── local/       # Room database and entities
│   │   ├── database/# Database definition
│   │   ├── entities/# Entity classes
│   │   └── doa/     # Data access objects
│   └── repository/  # Repository implementations
│
├── domain/
│   ├── model/       # Domain models (Image)
│   ├── repository/  # Repository interfaces
│   ├── usecase/     # Use cases
│   └── utils/       # Domain utilities
│
├── presentation/
│   ├── screens/
│   │   ├── main/    # Main grid screen (UI + ViewModel)
│   │   └── detail/  # Image detail screen
│   ├── mapper/      # Domain to UI mappers
│   └── components/  # Reusable UI components
│
├── di/              # Dependency injection (Hilt modules)
└── Application.kt
```

---
## 🔗 How It Works
- **User searches** → App fetches results from Pixabay API and caches them in Room.
- **Infinite scroll** → New pages automatically load while scrolling.
- **Tap on an image** → Navigates to Details screen with smooth shared element transitions.
- **Offline access** → Previously loaded results remain available without internet.
- **Elegant UI** → Modern Material 3 design with shimmer loading effects and beautiful animations.

---
## 🚀 Getting Started
### Requirements
- Android Studio Iguana or newer
- Minimum SDK 24+
- JDK 17

### Setup
1. Create a free [Pixabay API Key](https://pixabay.com/api/docs/).
2. Add your API key to your `local.properties`:
```properties
PIXABAY_API_KEY=your_api_key_here
```
3. Sync the project and run it.
