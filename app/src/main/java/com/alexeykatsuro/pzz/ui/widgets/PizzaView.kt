package com.alexeykatsuro.pzz.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import androidx.core.view.isVisible
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.alexeykatsuro.pzz.R
import com.alexeykatsuro.pzz.data.entities.PizzaVariant
import com.alexeykatsuro.pzz.data.entities.get
import com.alexeykatsuro.pzz.data.entities.hasSize
import com.alexeykatsuro.pzz.ui.home.PizzaTyped
import com.alexeykatsuro.pzz.utils.toLocalizedString
import com.bumptech.glide.Glide

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PizzaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val image by lazy<ImageView> { findViewById(R.id.image) }
    private val title by lazy<TextView> { findViewById(R.id.title) }
    private val radioBig by lazy<RadioButton> { findViewById(R.id.radio_big) }
    private val radioMedium by lazy<RadioButton> { findViewById(R.id.radio_medium) }
    private val radioThin by lazy<RadioButton> { findViewById(R.id.radio_thin) }
    private val textPriceBig by lazy<TextView> { findViewById(R.id.text_price_big) }
    private val textPriceMedium by lazy<TextView> { findViewById(R.id.text_price_medium) }
    private val textPriceThin by lazy<TextView> { findViewById(R.id.text_price_thin) }
    private val buttonToBasket by lazy<Button> { findViewById(R.id.button_to_basket) }
    private val textDescription by lazy<TextView> { findViewById(R.id.text_description) }

    private val compoundButtonListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (!buttonView.isPressed) return@OnCheckedChangeListener
            val type = when (buttonView) {
                radioBig -> PizzaVariant.Size.Big
                radioMedium -> PizzaVariant.Size.Medium
                radioThin -> PizzaVariant.Size.Thin
                else -> throw IllegalArgumentException("Unknown RadioButton ${buttonView.id} ")
            }
            onPizzaVariantTypeListener?.onPizzaVariantTypeChanged(pizzaTyped, type)
        }

    @set:CallbackProp
    var onPizzaVariantTypeListener: OnPizzaVariantTypeListener? = null

    private lateinit var pizzaTyped: PizzaTyped

    init {
        inflate(context, R.layout.view_item_pizza, this)

        sequenceOf(radioBig, radioMedium, radioThin).forEach {
            it.setOnCheckedChangeListener(compoundButtonListener)
        }
    }

    private fun getVariantViews(size: PizzaVariant.Size): Pair<RadioButton, TextView>? {
        return when (size) {
            PizzaVariant.Size.Big -> radioBig to textPriceBig
            PizzaVariant.Size.Medium -> radioMedium to textPriceMedium
            PizzaVariant.Size.Thin -> radioThin to textPriceThin
            PizzaVariant.Size.Unknown -> null
        }
    }

    @ModelProp
    fun setPizza(pizzaTyped: PizzaTyped) {
        this.pizzaTyped = pizzaTyped
        val pizza = pizzaTyped.pizza
        title.text = pizza.name
        textDescription.text = pizza.description


        PizzaVariant.Size.values().forEach { type ->
            val isVisible = pizza.variants.hasSize(type)
            val (radioButton, priceTextView) = getVariantViews(type) ?: return@forEach
            // Hide views for not existed PizzaVariantTypes
            radioButton.isVisible = isVisible
            priceTextView.isVisible = isVisible
            if (isVisible) {
                val variant = pizza.variants[type]!!
                radioButton.isChecked = pizzaTyped.selectedSize == type
                radioButton.text = context.getString(
                    R.string.pizza_type_variant,
                    type.toLocalizedString(context),
                    variant.diameter,
                    variant.weight
                )
                priceTextView.text = context.getString(R.string.price, variant.price)
            }
        }
    }

    @JvmOverloads
    @ModelProp
    fun setThumbnail(url: String? = null) {
        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .into(image);
    }


    @JvmOverloads
    @CallbackProp
    fun onToBasketClick(onClickListener: OnClickListener? = null) {
        buttonToBasket.setOnClickListener(onClickListener)
    }


    @OnViewRecycled
    fun clear() {
        image.setImageDrawable(null)
    }

    interface OnPizzaVariantTypeListener {
        fun onPizzaVariantTypeChanged(pizzaTyped: PizzaTyped, size: PizzaVariant.Size)

        companion object {
            inline operator fun invoke(
                crossinline block: (pizzaTyped: PizzaTyped, size: PizzaVariant.Size) -> Unit
            ) =
                object : OnPizzaVariantTypeListener {
                    override fun onPizzaVariantTypeChanged(
                        pizzaTyped: PizzaTyped,
                        size: PizzaVariant.Size
                    ) = block(pizzaTyped, size)
                }
        }
    }
}