package bit.chinjs1.languagetrainer;

/**
 * Created by Joshua on 3/16/2017.
 */

public class Question {

    public String noun;
    public String article;
    public int image;

    public Question(String noun, String article, int image) {

        this.noun = noun;
        this.article = article;
        this.image = image;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

