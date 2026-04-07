package se.catharsis.android.reader

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.graphics.text.LineBreaker
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import se.catharsis.android.reader.databinding.ActivityReaderBinding
import se.catharsis.android.reader.databinding.DialogSettingsBinding
import androidx.core.view.size
import androidx.core.view.get

class ReaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReaderBinding
    private lateinit var sharedPref: SharedPreferences
    private var favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.reader) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        sharedPref = getPreferences(MODE_PRIVATE)

        updateTextView()

        supportActionBar?.title = intent.getStringExtra(Intent.EXTRA_TITLE)
        binding.textView.text = intent.getStringExtra(Intent.EXTRA_TEXT)
    }

    override fun onSupportNavigateUp(): Boolean {
        setResult(RESULT_OK, Intent().putExtra("android.intent.extra.FAVORITE", favorite))
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reader_menu, menu)
        if (intent.hasExtra("android.intent.extra.FAVORITE")) {
            menu?.findItem(R.id.action_favorite)?.isVisible = true
            favorite = intent.getBooleanExtra("android.intent.extra.FAVORITE", false)
            toggleFav()
        } else {
            menu?.findItem(R.id.action_favorite)?.isVisible = false
        }
        updateTextView()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            val binding = DialogSettingsBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this).apply {
                setView(binding.root)
                binding.textCurrent.text = sharedPref.getFloat("textSize", 16f).toString()
                binding.textDecrease.setOnClickListener {
                    var textSize = sharedPref.getFloat("textSize", 16f)
                    textSize--
                    sharedPref.edit { putFloat("textSize", textSize) }
                    binding.textCurrent.text = textSize.toString()
                    updateTextView()
                }
                binding.textIncrease.setOnClickListener {
                    var textSize = sharedPref.getFloat("textSize", 16f)
                    textSize++
                    sharedPref.edit { putFloat("textSize", textSize) }
                    binding.textCurrent.text = textSize.toString()
                    updateTextView()
                }
                binding.lineHeightCurrent.text = sharedPref.getInt("lineHeight", 33).toString()
                binding.lineHeightDecrease.setOnClickListener {
                    var lineHeight = sharedPref.getInt("lineHeight", 33)
                    lineHeight--
                    sharedPref.edit { putInt("lineHeight", lineHeight) }
                    binding.lineHeightCurrent.text = lineHeight.toString()
                    updateTextView()
                }
                binding.lineHeightIncrease.setOnClickListener {
                    var lineHeight = sharedPref.getInt("lineHeight", 33)
                    lineHeight++
                    sharedPref.edit { putInt("lineHeight", lineHeight) }
                    binding.lineHeightCurrent.text = lineHeight.toString()
                    updateTextView()
                }
                binding.paddingCurrent.text = sharedPref.getInt("padding", 20).toString()
                binding.paddingDecrease.setOnClickListener {
                    var padding = sharedPref.getInt("padding", 20)
                    padding--
                    sharedPref.edit { putInt("padding", padding) }
                    binding.paddingCurrent.text = padding.toString()
                    updateTextView()
                }
                binding.paddingIncrease.setOnClickListener {
                    var padding = sharedPref.getInt("padding", 20)
                    padding++
                    sharedPref.edit { putInt("padding", padding) }
                    binding.paddingCurrent.text = padding.toString()
                    updateTextView()
                }
                binding.formatLeft.setOnClickListener {
                    sharedPref.edit { putInt("textAlignment", 0) }
                    updateTextView()
                }
                binding.formatCenter.setOnClickListener {
                    sharedPref.edit { putInt("textAlignment", 1) }
                    updateTextView()
                }
                binding.formatJustify.setOnClickListener {
                    sharedPref.edit { putInt("textAlignment", 3) }
                    updateTextView()
                }
                binding.formatRight.setOnClickListener {
                    sharedPref.edit { putInt("textAlignment", 2) }
                    updateTextView()
                }
                binding.sansSerif.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif") }
                    updateTextView()
                }
                binding.sansSerifLight.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif-light") }
                    updateTextView()
                }
                binding.sansSerifCondensed.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif-condensed") }
                    updateTextView()
                }
                binding.sansSerifBlack.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif-black") }
                    updateTextView()
                }
                binding.sansSerifThin.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif-thin") }
                    updateTextView()
                }
                binding.sansSerifMedium.setOnClickListener {
                    sharedPref.edit { putString("fontFamily", "sans-serif-medium") }
                    updateTextView()
                }
                binding.themeLight.setOnClickListener {
                    sharedPref.edit { putInt("theme", 0) }
                    updateTextView()
                }
                binding.themeSepia.setOnClickListener {
                    sharedPref.edit { putInt("theme", 1) }
                    updateTextView()
                }
                binding.themeDark.setOnClickListener {
                    sharedPref.edit { putInt("theme", 2) }
                    updateTextView()
                }
                setCancelable(true)
            }.create()
            dialog.show()
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 320f, resources.displayMetrics
            ).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
            true
        }

        R.id.action_favorite -> {
            favorite = !favorite
            toggleFav()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun toggleFav() {
        val theme = sharedPref.getInt("theme", 0)
        val textColor = when (theme) {
            1 -> getColor(R.color.sepia_text)
            2 -> getColor(R.color.dark_text)
            else -> getColor(R.color.light_text)
        }
        val iconRes = if (favorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        val icon = AppCompatResources.getDrawable(this, iconRes)?.apply {
            setTint(textColor)
        }
        binding.toolbar.menu.findItem(R.id.action_favorite).icon = icon
    }

    private fun updateTextView() {
        val theme = sharedPref.getInt("theme", 0)
        val bgColor = when (theme) {
            1 -> getColor(R.color.sepia_bg)
            2 -> getColor(R.color.dark_bg)
            else -> getColor(R.color.light_bg)
        }
        val textColor = when (theme) {
            1 -> getColor(R.color.sepia_text)
            2 -> getColor(R.color.dark_text)
            else -> getColor(R.color.light_text)
        }

        binding.reader.setBackgroundColor(bgColor)
        binding.toolbar.setBackgroundColor(bgColor)
        binding.toolbar.setTitleTextColor(textColor)
        binding.toolbar.navigationIcon?.setTint(textColor)
        val menu = binding.toolbar.menu
        for (i in 0 until menu.size) {
            menu[i].icon?.setTint(textColor)
        }
        binding.textView.setBackgroundColor(bgColor)
        binding.textView.setTextColor(textColor)

        binding.textView.setPadding(sharedPref.getInt("padding", 20))
        binding.textView.lineHeight = sharedPref.getInt("lineHeight", 33)
        binding.textView.typeface = Typeface.create(sharedPref.getString("fontFamily", "sans-serif"), Typeface.NORMAL)
        binding.textView.textSize = sharedPref.getFloat("textSize", 16f)
        val textAlignment = sharedPref.getInt("textAlignment", 0)
        if (textAlignment == 3) {
            binding.textView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.textView.gravity = Gravity.CENTER
        } else {
            binding.textView.justificationMode = LineBreaker.JUSTIFICATION_MODE_NONE
            when (textAlignment) {
                0 -> binding.textView.gravity = Gravity.START
                1 -> binding.textView.gravity = Gravity.CENTER
                2 -> binding.textView.gravity = Gravity.END
                else -> binding.textView.gravity = Gravity.START
            }
        }
    }
}
