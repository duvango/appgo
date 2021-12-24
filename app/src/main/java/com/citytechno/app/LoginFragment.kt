package com.citytechno.app

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.citytechno.app.databinding.FragmentLoginBinding
import com.citytechno.app.viewmodel.LoginViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private  val viewModel:LoginViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener { button->
          //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //viewModel.login()
            onLoginClicked(button)
        }
        addLiveDataObserver()
    }

    private fun addLiveDataObserver() {
        viewModel.loginRequestResultLiveData.observe(viewLifecycleOwner,{ isSuccessful->
            binding.progressBar.visibility=View.GONE
            binding.buttonLogin.isEnabled=true
            if(isSuccessful){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                binding.errorMessage.visibility=View.INVISIBLE
            }else{
                binding.errorMessage.text=getString(R.string.invalid_login_message)
                binding.errorMessage.visibility=View.VISIBLE
            }
        })
    }

    private fun onLoginClicked(button: View) {

        if(isValidLoginFields()){
            button.isEnabled=false
            binding.progressBar.visibility=View.VISIBLE
            viewModel.login(binding.editTextTextEmailAddress.text.toString(),binding.editTextNumberPassword.text.toString())
        }
    }

    private fun isValidLoginFields(): Boolean {

        if(binding.editTextTextEmailAddress.text.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text).matches()){
            binding.editTextTextEmailAddress.error=getString(R.string.invalid_email_message)
            return false
        }else{
            binding.editTextTextEmailAddress.error=null
        }
        if(binding.editTextNumberPassword.text.isEmpty()){
            binding.editTextNumberPassword.error=getString(R.string.invalid_password_message)
            return false
        }else{
            binding.editTextNumberPassword.error=null
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}