package modele;

import java.io.File;

public class ItemPickup extends Blueprint {

	private String title;
	private String desc;
	private int titleSize;
	private int titlePos;
	private int descSize;
	private int descPos;
	private String tradTitle;
	private String tradDesc;
	private String imagePath;
	
	private boolean isTtlTrnsltd;
	private boolean isDscTrnsltd;
	
	public ItemPickup(String name, String path) {
		super(name, path);
	}

	public ItemPickup(String path) {
		super(path);
	}
	
	public ItemPickup() {
		super();
	}

	public boolean setContent(String content) {
		isTtlTrnsltd = false;
		isDscTrnsltd = false;
		int idx = -1;
		
		setImagePath(getImagePath(content));
		
		//set up du titre
		idx = getFieldFlag(content,idx);
		if(idx==-1)return false;
		idx = setUpTitle(idx,content);
		
		//set up de la desc
		idx = getFieldFlag(content,idx);
		if(idx==-1)return false;
		idx = setUpDesc(idx,content);
		
		return true;
	}
	
	private int getFieldFlag(String content, int idx) {
		boolean checked = false;
		String temp;
		
		while(idx+32<content.length()&&!checked) {
			idx++;
			temp = ""+content.charAt(idx);
			if(temp.matches("[0-9A-F]")) {
				for (int j = 0;j<32;j++) {
					temp = ""+content.charAt(idx+j);
					if(temp.matches("[0-9A-F]")) {
						if(j==31)checked = true;
					}
					else break;
				}
			}
		}
		if(checked)return idx;
		else return -1;
	}

	private int setUpTitle(int idx,String content) {
		idx +=37; //on avance du flag jusqu'au titre
		titlePos = idx;
		titleSize = 1;
		while(content.charAt(idx+1)>31) {
			idx++;
			titleSize++;
		}
		title = content.substring(titlePos, titlePos+titleSize);
		return idx;
	}

	private Integer setUpDesc(int idx, String content) {
		idx+=37;
		descPos = idx;
		descSize = 1;
		while(content.charAt(idx+1)>31) {
			idx++;
			descSize++;
		}
		desc = content.substring(descPos, descPos+descSize);
		return idx;
	}

	private String getImagePath(String content) {
		int idxImage = content.indexOf("/Game/Textures/UI/ItemIcons/")+28;
		int idxImageFin = idxImage;
		while(content.charAt(idxImageFin)>31) {
			idxImageFin++;
		}
		return "UI"+File.separator+"ItemIcons"+File.separator+content.substring(idxImage,idxImageFin)+".png";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTradDesc() {
		if(isDscTrnsltd)return tradDesc;
		else return getDesc();
	}

	public void setTradDesc(String tradDesc) {
		this.tradDesc = tradDesc;
	}

	public String getTradTitle() {
		if(isTtlTrnsltd)return tradTitle;
		else return getTitle();
	}

	public void setTradTitle(String tradTitle) {
		this.tradTitle = tradTitle;
	}
	
	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public int getTitlePos() {
		return titlePos;
	}

	public void setTitlePos(int titlePos) {
		this.titlePos = titlePos;
	}

	public int getDescSize() {
		return descSize;
	}

	public void setDescSize(int descSize) {
		this.descSize = descSize;
	}

	public int getDescPos() {
		return descPos;
	}

	public void setDescPos(int descPos) {
		this.descPos = descPos;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean checkName(String toCheck) {
		if(this.titleSize>=toCheck.length())return true;
		return false;
	}
	
	public boolean checkDesc(String toCheck) {
		int length = toCheck.length();
		int nbReturn = 0;
		for (int i = 0; i < toCheck.length(); i++) {
			if (toCheck.charAt(i) == '\n') {
				nbReturn++;
			}
		}
		if(this.descSize>=nbReturn+length)return true;
		return false;
	}

	public void replaceTitle(String text) {
		if(checkName(text)) {
			StringBuilder sb = new StringBuilder(text);
			while(sb.length()<titleSize) {
				sb.append(" ");
			}
			setTradTitle(sb.toString());
			isTtlTrnsltd = true;
		}
	}

	public void replaceDesc(String text) {
		if(checkDesc(text)) {
			int nbReturn = 0;
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) == '\n') {
					nbReturn++;
				}
			}
			StringBuilder sb = new StringBuilder(text);
			while(sb.length()+nbReturn<descSize) {
				sb.append(" ");
			}
			setTradDesc(sb.toString());
			isDscTrnsltd = true;
		}
	}
	
}
