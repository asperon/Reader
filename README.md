# Reader

Reader is a simple reading activity for Android. It is meant to be included in any app where the user needs to read text on the screen. 

## Installation

Clone the repository, then add it as a module to your Android project.

## Usage

```kotlin
startActivity(Intent(it.context, ReaderActivity::class.java).apply {
    putExtra(Intent.EXTRA_TITLE, "Title")
    putExtra(Intent.EXTRA_TEXT, "Text")
})
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.


## License

[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
