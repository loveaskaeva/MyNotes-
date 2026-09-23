# Мои заметки

Приложение на Kotlin + Jetpack Compose + Room + Navigation.

## Реализованные самостоятельные задания

| Задание | Где реализовано |
|---|---|
| Изменение цвета кнопки и фона экрана | `NotesListScreen.kt` |
| Вывод в консоль текста из поля ввода | `NotesListScreen.kt` (кнопка «В консоль», Logcat, тег `MyNotesApp`) |
| Изменение цвета карточки + дата | `NotesListScreen.kt` → `NoteCard` |
| Вывод ID заметки на втором экране | `NoteDetailScreen.kt` (`LaunchedEffect`, Logcat, тег `MyNotesApp`) |
| Удаление заметки | `NoteDao.kt`, `NoteViewModel.kt`, `NoteDetailScreen.kt` |
| Поле timestamp в БД | `Note.kt` (Room entity) |
| AlertDialog при удалении | `NoteDetailScreen.kt` |

## Как открыть

1. Открыть папку проекта в Android Studio (Hedgehog/Iguana и новее).
2. Дождаться Gradle sync.
3. Запустить на эмуляторе/устройстве (minSdk 24).

## Как выложить в публичный репозиторий на GitHub

```bash
cd MyNotes
git init
git add .
git commit -m "Мои заметки: самостоятельные задания"
git branch -M main
git remote add origin https://github.com/<ваш_логин>/MyNotes.git
git push -u origin main
```

После создания репозитория на github.com обязательно откройте
Settings → General → Danger Zone → Change visibility → **Public**,
иначе преподаватель не сможет его проверить.
