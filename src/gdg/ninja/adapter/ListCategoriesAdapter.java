package gdg.ninja.adapter;

import gdg.nat.R;
import gdg.ninja.gameinfo.CategoriesInfo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListCategoriesAdapter extends BaseAdapter {
	private Context mContext;

	private static final int LOCKED_QUEST = 0;
	private static final int UNLOCKED_QUEST = 1;

	private List<CategoriesInfo> mList;

	public void setList(ArrayList<CategoriesInfo> list) {
		this.mList = list;
	}

	public void addList(ArrayList<CategoriesInfo> list) {
		this.mList.addAll(mList);
	}

	public void addItem(CategoriesInfo item) {
		this.mList.add(item);
	}

	public ListCategoriesAdapter(List<CategoriesInfo> list, Context context) {
		this.mList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public CategoriesInfo getItem(int position) {
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
		if (getItem(position - 1).getStt() < 3)
			return LOCKED_QUEST;
		else
			return UNLOCKED_QUEST;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
				case LOCKED_QUEST:
					convertView = View
							.inflate(
									mContext,
									R.layout.item_ltv_list_game_categories_locked,
									null);
					holder.unlockStatus = LOCKED_QUEST;
					convertView.setTag(holder);
					break;
				case UNLOCKED_QUEST:
					convertView = View.inflate(mContext,
							R.layout.item_ltv_list_game_categories, null);
					holder.imgAvatar = (ImageView) convertView
							.findViewById(R.id.img_avatar);
					holder.txtCategoryName = (TextView) convertView
							.findViewById(R.id.txt_categories_name);
					holder.txtCategoryDescription = (TextView) convertView
							.findViewById(R.id.txt_categories_description);
					holder.imgRateOne = (ImageView) convertView
							.findViewById(R.id.rate_one);
					holder.imgRateTwo = (ImageView) convertView
							.findViewById(R.id.rate_two);
					holder.imgRateThree = (ImageView) convertView
							.findViewById(R.id.rate_three);
					holder.unlockStatus = UNLOCKED_QUEST;
					convertView.setTag(holder);
					break;
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (holder.unlockStatus == UNLOCKED_QUEST) {
			CategoriesInfo item = getItem(position);
			if (item.getImgPath() != null)
				Picasso.with(mContext)
						.load(item.getImgPath().replace("assets",
								"file:///android_asset")).resize(200, 200)
						.placeholder(R.drawable.dummy_image)
						.into(holder.imgAvatar);
			holder.txtCategoryName.setText(item.getCateName());
			holder.txtCategoryDescription.setText(item.getCateDesc());
			int rate = item.getStt();
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
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView imgAvatar;
		public TextView txtCategoryName;
		public TextView txtCategoryDescription;
		public ImageView imgRateOne;
		public ImageView imgRateTwo;
		public ImageView imgRateThree;
		public int unlockStatus;
	}
}