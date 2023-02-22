package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 데이터를 꺼내서 앨범 객체로 변환
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        // 함수를 통해 바인딩
        setInit(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

//        binding.songLalacLayout.setOnClickListener{
//            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
//        }
        // 뷰 페이저 이용
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        // 탭 레이아웃, 미디에이터는 탭 레이아웃을 viewPager2와 연결하는 에디터 -> 동기화해줌
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root

    }

    private fun setInit(album: Album){
        binding.albumSingerNameTv.text = album.title.toString()
        binding.albumMusicTitleTv.text = album.singer.toString()
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
    }

}