import java.util.ArrayList;

public class MyGraph {
    private ArrayList<UserDetails> users;
    private ArrayList<ArrayList<Integer>> edges;

    /**
     * Initializes an instance of MyGraph.
     */
    public MyGraph() {
        users = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Adds a new user to the MyGraph instance.
     * @param ID - the unique identifier of the user.
     * @param name - the name of the user.
     * @param work - the work place of the user.
     * @param home - the home address of the user.
     * @param image - the image URL of the user.
     * @param likes - the likes of the user.
     */
    public void addUser(int ID , String name, String work, String home, String image, String likes) {
        UserDetails ud = new UserDetails(ID, name, work, home, image, likes);
        addUser(ud);
    }

    /**
     * Adds a new user to the MyGraph instance.
     * @param user - the UserDetails instance representing the new user.
     */
    public void addUser(UserDetails user) {
        users.add(user);
        edges.add(new ArrayList<Integer>());
    }

    /**
     * Adds or removes a friend connection between two users.
     * @param userID - the unique identifier of the first user.
     * @param friendID - the unique identifier of the second user.
     * @param bull - a boolean indicating whether to add or remove the friend connection.
     * @return the number of friend connections of the first user.
     */
    public int Friend(int userID, int friendID, boolean bull) {
        int userIndex = -1;
        int friendIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() == userID) {
                userIndex = i;
            }
            if (users.get(i).getID() == friendID) {
                friendIndex = i;
            }
            if (userIndex != -1 && friendIndex != -1) {
                break;
            }
        }
        if (userIndex != -1 && friendIndex != -1) {
            if(bull) {
                edges.get(userIndex).add(friendIndex);
                edges.get(friendIndex).add(userIndex);
                return edges.get(userIndex).size();
            }else{
                edges.get(userIndex).remove((Integer)friendIndex);
                edges.get(friendIndex).remove((Integer)userIndex);
                return edges.get(userIndex).size();
            }
        }
        return 0;
    }

    /**
     * Returns the list of all users in the MyGraph instance.
     * @return an ArrayList containing all the users in the MyGraph instance.
     */
    public ArrayList<UserDetails> getUsers() {
        return users;
    }

    /**
     * Returns the UserDetails instance representing the user with the given ID.
     * @param ID - the unique identifier of the user.
     * @return the UserDetails instance representing the user with the given ID, or null if no such user exists.
     */
    public UserDetails getUser(int ID) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() == ID) {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the friend connections of the given user.
     * @param user - the UserDetails instance representing the user.
     * @return a string containing the IDs of all friends of the user, separated by colons.
     */
    public String getUserEdges(UserDetails user) {
        int userIndex = users.indexOf(user);
        if (userIndex != -1) {
            ArrayList<Integer> edge = edges.get(userIndex);
            String s = "";
            for (int i = 0; i <edge.size() ; i++) {
                s = users.get(edge.get(i)).getID() + ":" + s;
            }
            return s;
        } else {
            return "";
        }
    }

    /**
     * Returns UserDetails object by matching with the provided userID
     * @param userID the ID of the user to search for
     * @return UserDetails object matching the provided userID or null if not found
     */
    public UserDetails getUserDetails(int userID) {
        for (UserDetails user : users) {
            if (user.getID() == userID) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns a list of a given user's friends.
     * @param userID the ID of the user whose friends are to be retrieved
     * @return an ArrayList of UserDetails objects representing the given user's friends
     */
    public ArrayList<UserDetails> getUserFriends(int userID) {
        ArrayList<UserDetails> userFriends = new ArrayList<>();
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() == userID) {
                userIndex = i;
                break;
            }
        }
        if (userIndex != -1) {
            ArrayList<Integer> friendIndices = edges.get(userIndex);
            for (int i = 0; i < friendIndices.size(); i++) {
                int friendIndex = friendIndices.get(i);
                UserDetails friend = users.get(friendIndex);
                userFriends.add(friend);
            }
        }
        return userFriends;
    }

    /**
     * Returns a list of UserDetails objects representing users who either live or work in a given place,
     * or whose name contains a specified string.
     * @param place the place to search for (either home or work)
     * @param c the character representing the place to search for ('w' for work, 'h' for home)
     * @param UserID the ID of the user who is not to be included in the search
     * @return an ArrayList of UserDetails objects representing users who match the given criteria
     */
    public ArrayList<UserDetails> getUsersByPlace(String place,char c,int UserID) {
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() != UserID) {
                if (c == 'w') {
                    if (users.get(i).getWork().equals(place)) {
                        indices.add(i);
                    }
                } else if (c == 'h') {
                    if (users.get(i).getHome().equals(place)) {
                        indices.add(i);
                    }
                }else{
                    if (users.get(i).getName().contains(place)) {
                        indices.add(i);
                    }
                }
            }
        }
        for (int index : indices) {
            usersInplace.add(users.get(index));
        }
        return usersInplace;
    }

    /**
     * Returns a list of UserDetails objects representing users who either live or work in a given place,
     * whose name contains a specified string, and whose work and home places match the given values.
     * @param work the work place to search for
     * @param home the home place to search for
     * @param search the string to search for in users' names
     * @param id the ID of the user who is not to be included in the search
     * @return an ArrayList of UserDetails objects representing users who match the given criteria
     */
    public ArrayList<UserDetails> getUsersByPlace(String work,String home,String search,int id){
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i<users.size();i++){
            if (users.get(i).getID() != id){
                if (users.get(i).getWork().equals(work) && users.get(i).getHome().equals(home)
                && users.get(i).getName().contains(search)){
                    indices.add(i);
                }
            }
        }
        for (int index : indices) {
            usersInplace.add(users.get(index));
        }
        if (usersInplace.isEmpty()) {
            return null;
        }
        return usersInplace;
    }

    /**
     * Returns a list of user details that match the given place, name, and user ID.
     * @param place a string representing a place (either work or home) where the user may be located.
     * @param placeOrName a string representing a name that may match the user's name or home place.
     * @param UserID the user ID of the user for whom to retrieve matches.
     * @param ch a character that indicates whether the place is the user's work or home.
     * @param ch2 a character that indicates whether the placeOrName is a name or home place.
     * @return an ArrayList of UserDetails objects that match the criteria, or null if no matches were found.
     */
    public ArrayList<UserDetails> getUsersByPlace(String place,String placeOrName, int UserID, char ch,char ch2){
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        // Loop through all users to find matches
        for (int i = 0; i<users.size();i++){
            // Exclude the user with the given ID
            if (users.get(i).getID() != UserID){
                if(ch == 'w' && ch2 == 's'){
                    if (users.get(i).getWork().equals(place)&&
                    users.get(i).getName().contains(placeOrName)){
                        indices.add(i);
                    }
                }
                if(ch == 'h'&&ch2 == 's'){
                    if (users.get(i).getHome().equals(place) &&
                    users.get(i).getName().contains(placeOrName)){
                        indices.add(i);
                    }
                }
                if (ch == 'w'&&ch2 == 'h') {
                    if (users.get(i).getWork().equals(place)&&
                    users.get(i).getHome().equals(placeOrName)){
                        indices.add(i);
                    }
                }
            }
        }
        // Get the users from the indices found
        for (int index : indices) {
            usersInplace.add(users.get(index));
        }
        // Return null if no matches were found
        if (usersInplace.isEmpty()) {
            return null;
        }
        return usersInplace;
    }

    /**
     * Returns a list of user details that match the given place, friend ID, and user ID.
     * @param place a string representing a place (either work or home) where the friend of a friend may be located.
     * @param FriendID the ID of the friend of the user for whom to retrieve matches.
     * @param ch a character that indicates whether the place is the friend's work or home.
     * @param UserID the user ID of the user for whom to retrieve matches.
     * @return an ArrayList of UserDetails objects that match the criteria, or null if no matches were found.
     */
    public ArrayList<UserDetails> getUsersByPlace(String place, int FriendID,char ch,int UserID) {
        ArrayList<UserDetails> FriendOfFriends = getUserFriends(FriendID);
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        // Loop through all friends of the friend to find matches
        for(UserDetails details : FriendOfFriends){
            if(ch == 'w') {
                if (details.getWork().equals(place)) {
                    usersInplace.add(details);
                }
            }else{
                if (details.getHome().equals(place)) {
                    usersInplace.add(details);
                }
            }
        }
        // Return null if no matches were found
        if (usersInplace.isEmpty()) {
            return null;
        }
        return usersInplace;
    }

    /**
     *Returns a list of user details that match the work or home place of the user with the given ID.
     * @param id the ID of the user for whom to retrieve matches.
     * @return an ArrayList of UserDetails objects representing the users that live or work in the same place
     */
    public ArrayList<UserDetails> getUsersByPlace(int id){
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        UserDetails user = getUserDetails(id);
        ArrayList<Integer> indices = new ArrayList<>();

        // Iterate through all users to find those who live or work in the same place as the given user
        for (int i = 0; i<users.size();i++){
            if (users.get(i).getID() != id){
                if (users.get(i).getWork().equals(user.getWork())){
                    indices.add(i);
                } else if (users.get(i).getHome().equals(user.getHome())) {
                    indices.add(i);
                }
            }
        }

        // Retrieve UserDetails objects for users in the same place and add them to an ArrayList
        for (int index : indices) {
            usersInplace.add(users.get(index));
        }

        // If no users in the same place are found, return null
        if (usersInplace.isEmpty()) {
            return null;
        }

        return usersInplace;
    }

    /**
     * Retrieves the friends of a given user that live or work in the same place
     * @param FriendsFriend an ArrayList of UserDetails objects representing the friends of the user
     * @param work the work location of the user
     * @param home the home location of the user
     * @param UserFriend a String containing the IDs of the user's friends separated by colons
     * @return an ArrayList of UserDetails objects representing the user's friends that live or work in the same place
     */
    public ArrayList<UserDetails> getUserFriendsByPlace(ArrayList<UserDetails> FriendsFriend,
    String work,String home,String UserFriend){
        String [] friend = UserFriend.split(":");
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        // Iterate through all the user's friends to find those who live or work in the same place
        for (int i = 0; i < FriendsFriend.size() ; i++){
            for (int j = 0; j <friend.length ; j++) {
                int friendnumber = Integer.parseInt(friend[j]);
                if(friendnumber == FriendsFriend.get(i).getID()){
                    if (FriendsFriend.get(i).getWork().equals(work) &&
                    FriendsFriend.get(i).getHome().equals(home)){
                        indices.add(i);
                    }
                }
            }
        }

        // Retrieve UserDetails objects for the user's friends in the same place and add them to an ArrayList
        for (int index : indices) {
            usersInplace.add(FriendsFriend.get(index));
        }

        // If no friends in the same place are found, return null
        if (usersInplace.isEmpty()) {
            return null;
        }

        return usersInplace;
    }

    /**
     * Retrieves the friends of a given user that live or work in a specified place
     * @param FriendsFriend an ArrayList of UserDetails objects representing the friends of the user
     * @param place the location to search for (either work or home)
     * @param ch a character indicating whether to search for friends with the given location as their work or home
     * @return an ArrayList of UserDetails objects representing the user's friends that live or work in the specified place
     */
    public ArrayList<UserDetails> getUserFriendsByPlace(ArrayList<UserDetails> FriendsFriend,
    String place,char ch){
        String[] friend = null;
        if(ch == 's') {
            friend = place.split(":");
        }
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < FriendsFriend.size() ; i++){
            if (ch == 'w') {
                if (FriendsFriend.get(i).getWork().equals(place)) {
                    indices.add(i);
                }
            } else if (ch == 'h') {
                if (FriendsFriend.get(i).getHome().equals(place)) {
                    indices.add(i);
                }
            }else if(ch == 's'){
                for (int j = 0; j <friend.length ; j++) {
                    if(FriendsFriend.get(i).getID() == Integer.parseInt(friend[j])){
                        indices.add(i);
                    }
                }
            }
        }
        for (int index : indices) {
            usersInplace.add(FriendsFriend.get(index));
        }
        if (usersInplace.isEmpty()) {
            return null;
        }
        return usersInplace;
    }

    /**
     *This method returns a list of UserDetails objects, which represent the friends of a user who have a specific place of work or home.
     * @param FriendsFriend an ArrayList of UserDetails objects representing the friends of the user.
     * @param place a String representing the place of work or home to search for.
     * @param placeOrSameFriend a String representing the place of work or home of a specific friend to search for, or the IDs of the specific friends.
     * @param ch a char representing the type of place to search for ('w' for work or 'h' for home).
     * @param ch2 a char representing the type of search to perform ('s' for specific friend or placeOrSameFriend string, 'a' for all friends).
     * @return an ArrayList of UserDetails objects representing the friends of the user who have a specific place of work or home.
     * */
    public ArrayList<UserDetails> getUserFriendsByPlace(ArrayList<UserDetails> FriendsFriend, String place, String placeOrSameFriend, char ch, char ch2) {
        String[] friend = null;
        // Check if searching for a specific friend
        if (ch2 == 's') {
            friend = placeOrSameFriend.split(":");
        }
        ArrayList<UserDetails> usersInplace = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        // Loop through all friends to find matching place
        for (int i = 0; i < FriendsFriend.size(); i++) {
            // Check if searching for both work and home place and place matches
            if (ch == 'w' && ch2 == 'h') {
                if (FriendsFriend.get(i).getWork().equals(place) && FriendsFriend.get(i).getHome().equals(placeOrSameFriend)) {
                    indices.add(i);
                }
            } else if (ch2 == 's') {
                // Check if searching for a specific friend and friend ID matches
                for (int j = 0; j < friend.length; j++) {
                    int friendNumber = Integer.parseInt(friend[j]);
                    if (friendNumber == FriendsFriend.get(i).getID()) {
                        // Check if searching for work or home place and place matches
                        if (ch == 'w') {
                            if (FriendsFriend.get(i).getWork().equals(place)) {
                                indices.add(i);
                            }
                        } else if (ch == 'h') {
                            if (FriendsFriend.get(i).getHome().equals(place)) {
                                indices.add(i);
                            }
                        }
                    }
                }
            }
        }
        // Add matching friends to a new ArrayList
        for (int index : indices) {
            usersInplace.add(FriendsFriend.get(index));
        }
        // If no matching friends found, return null
        if (usersInplace.isEmpty()) {
            return null;
        }
        // Return ArrayList of matching friends
        return usersInplace;
    }

    /**
     * Check if the given ID exists in the list of users.
     *
     * @param id the ID to check
     * @return true if the ID exists, false otherwise
     */
    public boolean check(int id){
        for (UserDetails details : users){
            if (details.getID() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a user has any friends or not. If the user has at least one friend,
     * return 'b', if the user has no friends, return 'a', and if the user does not exist,
     * return 'c'.
     *
     * @param id the ID of the user to check
     * @return 'a' if the user has no friends, 'b' if the user has at least one friend, and 'c' if the user does not exist
     */
    public char specialCheck(int id){
        for (int i = 0; i <users.size() ; i++) {
            if(users.get(i).getID() == id){
                if(edges.get(i).isEmpty()){
                    return 'a';
                }else {
                    return 'b';
                }
            }
        }
        return 'c';
    }

}