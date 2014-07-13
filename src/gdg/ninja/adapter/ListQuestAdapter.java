package gdg.ninja.adapter;

import gdg.nat.R;
import gdg.ninja.gameinfo.QuestInfo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

public class ListQuestAdapter extends BaseAdapter {
	private Context mContext;

	private List<QuestInfo> mList;
	private int itemSize;

	private static final int LOCKED_QUEST = 0;
	private static final int UNLOCKED_QUEST = 1;

	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
	}

	public void setList(ArrayList<QuestInfo> list) {
		this.mList = list;
	}

	public void addList(ArrayList<QuestInfo> list) {
		this.mList.addAll(mList);
	}

	public void addItem(QuestInfo item) {
		this.mList.add(item);
	}

	public ListQuestAdapter(List<QuestInfo> list, Context context) {
		this.mList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public QuestInfo getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return UNLOCKED_QUEST;
		if (getItem(position - 1).getQuestStt() < 3)
			return LOCKED_QUEST;
		else
			return UNLOCKED_QUEST;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		QuestInfo item = getItem(position);
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
				case LOCKED_QUEST:
					convertView = View.inflate(mContext,
							R.layout.item_ltv_list_game_locked, null);
					holder.layout = (RelativeLayout) convertView
							.findViewById(R.id.layout);
					holder.imgAvatar = (ImageView) convertView
							.findViewById(R.id.img_avatar);
					holder.unlockStatus = LOCKED_QUEST;
					break;

				case UNLOCKED_QUEST:
					convertView = View.inflate(mContext,
							R.layout.item_ltv_list_game, null);
					holder.layout = (RelativeLayout) convertView
							.findViewById(R.id.layout);
					holder.imgAvatar = (ImageView) convertView
							.findViewById(R.id.img_avatar);
					holder.imgRateOne = (ImageView) convertView
							.findViewById(R.id.rate_one);
					holder.imgRateTwo = (ImageView) convertView
							.findViewById(R.id.rate_two);
					holder.imgRateThree = (ImageView) convertView
							.findViewById(R.id.rate_three);
					convertView.setTag(holder);
					holder.unlockStatus = UNLOCKED_QUEST;
					break;
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (holder.layout.getHeight() < itemSize) {
			LayoutParams param = new LayoutParams(itemSize, itemSize);
			holder.layout.setLayoutParams(param);
		}
		if (holder.unlockStatus == UNLOCKED_QUEST) {
			Picasso.with(mContext)
					.load(item.getImgPath().replace("assets",
							"file:///android_asset"))
					.placeholder(R.drawable.dummy_image).into(holder.imgAvatar);
			int rate = item.getQuestStt();
			switch (rate) {
				case 6:
					holder.imgRateThree.setImageResource(R.drawable.ic_rate_on);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_on);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
					break;
				case 5:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_half);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_on);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
					break;
				case 4:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_on);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
					break;
				case 3:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_half);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
					break;
				case 2:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
					break;
				case 1:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_half);
					break;
				default:
					holder.imgRateThree
							.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
					holder.imgRateOne.setImageResource(R.drawable.ic_rate_off);
					break;
			}
		} else {
			Picasso.with(mContext)
					.load(item.getImgPath().replace("assets",
							"file:///android_asset"))
					.placeholder(R.drawable.dummy_image).into(holder.imgAvatar);
		}
		return convertView;
	}

	public class ViewHolder {
		public RelativeLayout layout;
		public ImageView imgAvatar;
		public ImageView imgRateOne;
		public ImageView imgRateTwo;
		public ImageView imgRateThree;
		public int unlockStatus;
	}
}