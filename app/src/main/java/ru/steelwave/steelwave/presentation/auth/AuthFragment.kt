package ru.steelwave.steelwave.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentAuthBinding
import ru.steelwave.steelwave.presentation.CustomToast
import ru.steelwave.steelwave.presentation.MainActivity

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingView()
        setListenersInView()
    }

    private fun setListenersInView(){
        with(binding){
            btnNext.setOnClickListener {
                clGreeting.visibility = View.GONE
                clAuth.visibility = View.VISIBLE
            }
            btnSignin.setOnClickListener {
                Intent(requireContext(), MainActivity::class.java).also {
                    startActivity(it)
                    requireActivity().finish()
                    CustomToast.toastDefault(requireContext(), "Вы успешно авторизованы")
                }
            }
            btnBack.setOnClickListener{
                clAuth.visibility = View.GONE
                clGreeting.visibility = View.VISIBLE
                etLogin.text = null
                etPassword.text = null
                etSecretWord.text = null
            }
        }
    }

    private fun settingView() {
        setRulesColorUnderline()
    }

    private fun setRulesColorUnderline() {
        val fullText = requireContext().getText(R.string.greetings_in_auth)
        val highlightedText2 = "правилами использования веб-приложения."

        val spannableString = SpannableString(fullText)

        val underlineSpan = UnderlineSpan()
        val foregroundColorSpan = ForegroundColorSpan(requireContext().getColor(R.color.sw_purple))

        val personalDataClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToRulesFragment())
            }
        }

        spannableString.setSpan(
            personalDataClickableSpan,
            fullText.indexOf(highlightedText2),
            fullText.indexOf(highlightedText2) + highlightedText2.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            underlineSpan,
            fullText.indexOf(highlightedText2),
            fullText.indexOf(highlightedText2) + highlightedText2.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            foregroundColorSpan,
            fullText.indexOf(highlightedText2),
            fullText.indexOf(highlightedText2) + highlightedText2.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvGreetings.text = spannableString
        binding.tvGreetings.movementMethod = LinkMovementMethod.getInstance()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}