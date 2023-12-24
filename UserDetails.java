/**
 * UserDetails class represents a user's details.
 */
public class UserDetails {

    private int ID;
    private String name,work,home,image,likes;

    /**
     * Constructor for creating an empty UserDetails object.
     */
    public UserDetails(){
        ID = 0;
        name = "";
        work = "";
        home = "";
        image = "";
        likes = "";
    }

    /**
     * Constructor for creating a UserDetails object with all fields.
     * @param ID The ID of the user.
     * @param name The name of the user.
     * @param work The work information of the user.
     * @param home The home information of the user.
     * @param image The image path of the user.
     * @param likes The likes of the user.
     */
    public UserDetails(int ID, String name , String work, String home,
    String image,String likes){
        this.ID = ID;
        this.name = name;
        this.work = work;
        this.home = home;
        this.image = image;
        this.likes = likes;
    }

    /**
     * Getter method for name
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for ID
     * @return user ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter method for work
     * @return Work
     */
    public String getWork() {
        return work;
    }

    /**
     * Setter method for work
     * @param work
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * Getter method for home
     * @return user Home
     */
    public String getHome() {
        return home;
    }

    /**
     * Setter method for home
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Getter method for Image
     * @return Image
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter method for image
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter method for Like
     * @return UserLikes
     */
    public String getLikes() {
        return likes;
    }

    /**
     * Setter method for likes
     * @param likes
     */
    public void setLikes(String likes) {
        this.likes = likes;
    }

}
