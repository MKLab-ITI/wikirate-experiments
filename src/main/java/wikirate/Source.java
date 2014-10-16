package wikirate;

public class Source {

	private final String id;
	private final String title;
	private final String url;
	
	public Source(String id, String title, String url){
		this.id = id;
		this.title = title;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Source [id=" + id + ", title=" + title + ", url=" + url + "]";
	}
	
}
