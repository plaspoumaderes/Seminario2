package com.seminario2.mecanicaapp.ui.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.model.ChatModel
import com.seminario2.mecanicaapp.model.ChatRequest
import com.seminario2.mecanicaapp.model.FixModelResponse
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var fixModelResponse: FixModelResponse

    companion object {
        const val FIX_KEY = "fix-key"
        fun newInstance(fixModelResponse: FixModelResponse): ChatFragment {
            return ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(FIX_KEY, Gson().toJson(fixModelResponse))
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            fixModelResponse =
                Gson().fromJson(it.getString(FIX_KEY), FixModelResponse::class.java).apply {
                    viewModel.getChatsbyFixId(ChatRequest(this._id))
                }
        }
        loadAdapter()
        addListener()
        addObservable()
    }

    private fun addListener() {
        fr_chat_send.setOnClickListener {
            if (fr_chat_input.text.toString().isNotEmpty()) {
                val insertChat = ChatModel(
                    fixModelResponse._id,
                    loginResponse.fullName,
                    fr_chat_input.text.toString()
                )
                viewModel.postInsertChat(insertChat)
                viewModel.getChatsbyFixId(ChatRequest(fixModelResponse._id))
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getChatsbyFixId(
                ChatRequest(
                    fixModelResponse._id
                )
            )
        }
    }

    private fun addObservable() {
        viewModel.postInsertChat.observe(
            viewLifecycleOwner,
            Observer { response ->
                if (response.isSuccessful) {
                    if (response.body() as Boolean) {
                        fr_chat_input.setText("")
                    }
                }
            })
        viewModel.postChatFixedHistory.observe(
            viewLifecycleOwner,
            Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        swipeRefreshLayout.isRefreshing = false
                        chatAdapter.updateItems(ArrayList(it))
                    }
                }
            }
        )
    }

    private fun loadAdapter() {
        chatAdapter = ChatAdapter(loginResponse.fullName, fixModelResponse.garage)
        val layoutManagerHistory = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_chat_recycler.isNestedScrollingEnabled = false
        fr_chat_recycler.layoutManager = layoutManagerHistory
        fr_chat_recycler.adapter = chatAdapter
    }
}