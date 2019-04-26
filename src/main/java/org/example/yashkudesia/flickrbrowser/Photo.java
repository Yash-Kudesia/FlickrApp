package org.example.yashkudesia.flickrbrowser;

class Photo {
    private String Title;
    private String Author;
    private String Author_id;
    private String Link;
    private String Tag;
    private String Image;

    public Photo(String title, String Authorid, String author, String link, String tag, String image) {
        Title = title;
        Author = author;
        Author_id = Authorid;
        Link = link;
        Tag = tag;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getAuthor_id() {
        return Author_id;
    }

    public String getLink() {
        return Link;
    }

    public String getTag() {
        return Tag;
    }

    public String getImage() {
        return Image;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Author_id='" + Author_id + '\'' +
                ", Link='" + Link + '\'' +
                ", Tag='" + Tag + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
