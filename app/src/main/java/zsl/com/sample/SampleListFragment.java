package zsl.com.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SampleListFragment extends Fragment{

	private static final String ARG_POSITION = "position";

	private DiscoverListView mListView;
	private ArrayList<String> mListItems;

	private int mPosition;
	private View mHeaderView;

	public static SampleListFragment newInstance(int position) {
		SampleListFragment f = new SampleListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt(ARG_POSITION);

		mListItems = new ArrayList<String>();

		for (int i = 1; i <= 100; i++) {
			mListItems.add(i + ". item - currnet page: " + (mPosition + 1));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, null);

		mListView = (DiscoverListView) v.findViewById(R.id.listView);

        // 如果不去掉listview的focus，
        mListView.setFocusable(false);

		View placeHolderView = inflater.inflate(R.layout.view_head, mListView, false);
		placeHolderView.setBackgroundColor(0xFFFFFFFF);
		mListView.addHeaderView(placeHolderView);
		mHeaderView = placeHolderView;

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, mListItems));
	}

	public void enableListScroll(boolean enable) {
		mListView.enableScroll(enable);
	}
}