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

public class ListCategoriesAdapter extends BaseAdapter {
	private Context mContext;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.item_ltv_list_game_categories, null);
			holder = new ViewHolder();
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CategoriesInfo item = getItem(position);
		holder.imgAvatar.setImageResource(item.getImgId());
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
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_half);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_on);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
				break;
			case 4:
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_on);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
				break;
			case 3:
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_half);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
				break;
			case 2:
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_on);
				break;
			case 1:
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_half);
				break;
			default:
				holder.imgRateThree.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateTwo.setImageResource(R.drawable.ic_rate_off);
				holder.imgRateOne.setImageResource(R.drawable.ic_rate_off);
				break;
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
	}
}