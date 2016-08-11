package com.example.fragmentbestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsTitleFragment extends Fragment {
	private ListView newsTitleListView;
	
	private List<News> newsList;
	
	private NewsAdapter adapter;
	
	private boolean isTwoPane;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		newsList = getNews();
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.news_title_frag, container, false);
		newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				News news = newsList.get(position);
				if(isTwoPane){
					NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
					newsContentFragment.refresh(news.getTitle(), news.getContent());
				}
				else{
					NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
				}
			}
		});
		return view;
	}

	private List<News> getNews() {
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("台湾当局被美国打脸 台舆论强烈要求蔡英文登岛");
		news1.setContent("【环球时报驻台北特约记者 林曦】南海仲裁案做出不利于台湾拥有太平岛“主权”的裁决，台湾海军军舰13日提前启航奔赴太平岛，蔡英文特别登上军舰发表谈话“宣示主权”。不过在一些岛内媒体看来，她的动作显得很及时，话也说得很铿锵，“但实际上却只有内销的作用。对国际权力政治而言，此举仅只是护土的半套戏码而已”。");
		newsList.add(news1);
		
		News news2 = new News();
		news2.setTitle("评论：中国若无在南海对抗美挑衅勇气还当啥大国");
		news2.setContent("非法组建的南海仲裁案临时仲裁庭星期二公布“仲裁结果”后，美国呼应的调门最高。除了美国国务院、白宫发言人分别发表谈话，宣称“仲裁具有法律约束力”外，参众两院多个委员会的牵头人或议员做了措辞更激烈的发言，比如要求通过空中及海上巡逻“定期挑战中国过度的海洋声索要求”等。日本的表态与华盛顿如出一辙，就像与美方“对过词”一样。");
		newsList.add(news2);
		
		return newsList;
	}

}
