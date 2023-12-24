import java.io.*;

/**
 * The File class is responsible for reading data from a text file and writing data to it.
 * This class has methods to add user information to a file, and also to read data from a file.
 * @param graph The graph data structure used to store user information
 */
public class File {

    private MyGraph graph;

    public File(MyGraph graph){
        this.graph = graph;
    }

    /**
     * This method is responsible for adding the user details to a text file named "Detail.txt".
     * This method loops through all user details stored in the graph and writes them to the file.
     */
    public void addToFile(){
        FileOutputStream outputStream;
        PrintWriter printWriter = null;
        try {
            outputStream = new FileOutputStream("Detail.txt");
            printWriter = new PrintWriter(outputStream);

            for (UserDetails details : graph.getUsers()) {
                int id = details.getID();
                String friend = graph.getUserEdges(details);
                String allData = details.getID() + "," + details.getName() + ","
                    + details.getWork() + "," + details.getHome() + "," + friend
                    + "," + details.getImage() + "," + details.getLikes();
                printWriter.println(allData);
            }
        }
        catch (IOException e)
        {
            System.out.println("Sorry, there has been a problem opening or writing to the file");
            System.out.println("/t" + e);
        }
        finally
        {
            if (printWriter != null){
                printWriter.close();
            }
        }
    }

    /**
     * This method is responsible for reading data from a text file named "Detail.txt" and adding them to the graph data structure.
     * The method reads each line of the file, splits it into individual user details, and adds them to the graph.
     */
    public void readFromAFile(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try
        {
            fileReader = new FileReader("Detail.txt");
            bufferedReader = new BufferedReader(fileReader);
            String nextLine = bufferedReader.readLine();
            while (nextLine != null)
            {
                String[] list = nextLine.split(",");
                int ID = Integer.parseInt(list[0]);
                String name = list[1];
                String work = list[2];
                String home = list[3];
                String friends = "";
                String image = "";
                String like = "";
                try {
                    friends = list[4];

                }catch (ArrayIndexOutOfBoundsException ai){
                    //
                }
                try {
                    image = list[5];

                }catch (ArrayIndexOutOfBoundsException ai){
                    //
                }
                try {
                    like = list[6];

                }catch (ArrayIndexOutOfBoundsException ai){
                    //
                }
                graph.addUser(ID,name,work,home,image,like);
                if(friends.equals("")){

                }else{
                    String[] friendIds = friends.split(":");
                    for (String friendId : friendIds) {
                        int friend = Integer.parseInt(friendId);
                        graph.Friend(ID, friend,true);
                    }
                }
                nextLine = bufferedReader.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("file was not found");
        }
        catch (IOException e)
        {
            System.out.println("IO Error reading from file: " + e);
        }
        finally{
            if (bufferedReader != null){
                try{
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

}
