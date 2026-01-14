# Reader

Reader is a simple reading activity for Android. It is meant to be included in any app where the user needs to read text on the screen. 

## Installation

Clone the repository, then add it as a module to your Android project.

## Usage

```kotlin
startActivity(Intent(context, ReaderActivity::class.java).apply {
    putExtra(Intent.EXTRA_TITLE, "Title")
    putExtra(Intent.EXTRA_TEXT, "Text")
})
```

To use the favorite button feature, send an additional extra Intent.EXTRA_ARCHIVAL to reflect the state of the favorite button, the reader will return the current state of it as a result when its closed.

```kotlin
val showContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == RESULT_OK) {
        result.data?.getBooleanExtra(Intent.EXTRA_ARCHIVAL, false)?.let { fav ->
        }
    }
}

val intent = Intent(context, ReaderActivity::class.java).apply {
    putExtra(Intent.EXTRA_TITLE, story.title)
    putExtra(Intent.EXTRA_TEXT, story.content)
    putExtra(Intent.EXTRA_ARCHIVAL, story.favorite)
}
showContent.launch(intent)
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.


## License

[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
