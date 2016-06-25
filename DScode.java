import java.io.*;
import java.util.Vector;
import java.util.Scanner;

public class Main {
    public static void main (String args[])
    {
        String ln1;
        String ln2;
        String ln3;
        String[] t_user;
        String[] t_tweet;
        String[] t_friend;
        String[][] user = new String [20000][4];
        String[][] tweet = new String [20000][4];
        String[][] friend = new String [20000][3];
        Vector lines1 = new Vector();
        Vector lines2 = new Vector();
        Vector lines3 = new Vector();
        int n_user = 0; //user 배열의 크기
        int n_friend = 0;//friend ''
        int n_tweet = 0;//tweet ''


        //////////////////////////////////////////////////////////////////////

        //텍스트 파일을 불러와 배열에 넣는 부분
        try
        {
            FileReader fr1=new FileReader("C:\\DS_project\\test-1\\user.txt");
            BufferedReader br1=new BufferedReader(fr1);

            while((ln1=br1.readLine())!=null){
                lines1.addElement(ln1);
            }


            t_user = new String[lines1.size()];

            for (int i = 0; i < t_user.length; i++)
            {
                t_user[i] = (String) lines1.elementAt(i);
                user[i/4][i%4] = t_user[i];
            }

            n_user = t_user.length/4;

        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }

        try
        {
            File file = new File("C:\\DS_project\\test-1\\word.txt");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            //한글 깨짐 현상 방지하기 위함

            ln2 = null;

            while((ln2=br2.readLine())!=null)
            {
                lines2.addElement(ln2);
            }


            t_tweet = new String[lines2.size()];

            for (int i = 0; i < t_tweet.length; i++)
            {
                t_tweet[i] = (String) lines2.elementAt(i);
                tweet[i/4][i%4] = t_tweet[i];
            }

            n_tweet = t_tweet.length/4;

        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }

        try
        {
            FileReader fr3=new FileReader("C:\\DS_project\\test-1\\friend.txt");
            BufferedReader br3=new BufferedReader(fr3);

            while((ln3=br3.readLine())!=null){
                lines3.addElement(ln3);
            }


            t_friend = new String[lines3.size()];

            for (int i = 0; i < t_friend.length; i++)
            {
                t_friend[i] = (String) lines3.elementAt(i);
                friend[i/3][i%3] = t_friend[i];
            }

            n_friend = t_friend.length/3;

        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }


        //////////////////////////////////////////////////////////

        //Statistics 계산부분
        //user별 friend 수
        int [] user_f = new int [n_user];

        for (int i = 0; i < n_user; i++)
        {
            user_f[i] = 0;
        }
        for (int i = 0; i < n_friend; i++)
        {
            for (int j = 0; j < n_user; j++)
            {
                if (friend[i][0].equals(user[j][0]))
                {
                    ++user_f[j];
                }
            }
        }

        //user별 tweet 수
        int [] user_t = new int [n_user];

        for (int i = 0; i < n_user; i++)
        {
            user_t[i] = 0;
        }
        for (int i = 0; i < n_tweet; i++)
        {
            for (int j = 0; j < n_user; j++)
            {
                if (tweet[i][0].equals(user[j][0]))
                {
                    ++user_t[j];
                }
            }
        }

        ///////////////////////////////////////////////////////

        //Interface 구현
        Scanner key = new Scanner(System.in);
        while(true)
        {
            System.out.println("0. Read data files");
            System.out.println("1. Display statistics");
            System.out.println("2. Top 5 most tweeted words");
            System.out.println("3. Top 5 most tweeted users");
            System.out.println("4. Find users who tweeted a word (e.g., '연세대')");
            System.out.println("5. Find all people who are friends of the above users");
            System.out.println("6. Delete all mentions of a word");
            System.out.println("7. Delete all users who mentioned a word");
            System.out.println("8. Find strongly connected components");
            System.out.println("9. Find shortest path from a given user");
            System.out.println("99. Quit");
            System.out.println("Select Menu : ");
            int choice = key.nextInt();

            ////////////////////////////////////////////////////////////

            if(choice == 0)
            {
                System.out.println("Total users: "+ n_user);
                System.out.println("Total friendship records: "+ n_friend);
                System.out.println("Total tweets: "+ n_tweet);
                System.out.println("");
            }

            ////////////////////////////////////////////////////////////////

            else if (choice == 1)
            {
                int min_f = 1000;
                int max_f = 0;
                int total_f = 0;
                float aver_f = 0;

                int min_t = 1000;
                int max_t = 0;
                int total_t = 0;
                float aver_t = 0;

                for (int i = 0; i < n_user; ++i)
                {
                    if (user_f[i] > max_f)
                        max_f = user_f[i];
                    if (user_f[i] < min_f)
                        min_f = user_f[i];
                    total_f = total_f + user_f[i];
                }

                for (int i = 0; i < n_user; ++i)
                {
                    if (user_t[i] > max_t)
                        max_t = user_t[i];
                    if (user_t[i] < min_t)
                        min_t = user_t[i];
                    total_t = total_t + user_t[i];
                }

                aver_f = (float)total_f / (float)n_user;
                aver_t = (float)total_t / (float)n_user;

                System.out.printf("Average number of friends: %.2f\n", aver_f);
                System.out.println("Minimum number of friends: " + min_f);
                System.out.println("Maximum number of friends: " + max_f);
                System.out.println("");
                System.out.printf("Average tweets per user: %.2f\n", aver_t);
                System.out.println("Minimum tweets per user: " + min_t);
                System.out.println("Maximum tweets per user: " + max_t);
                System.out.println("");
            }

            /////////////////////////////////////////////////////////////////////

            else if (choice == 2)
            {
                boolean tf = false;
                int k = 0;
                int index = 0;
                String [] word_t = new String[n_tweet]; //tweet된 단어들을 한번씩만 담는 배열만든다
                int [] word_n = new int[n_tweet]; //tweet 횟수를 담는 배열

                for (int i = 0;  i < n_tweet; ++i)
                {
                    tf = false;
                    k = 0;
                    while (k < word_t.length)
                    {
                        if (tweet[i][2].equals(word_t[k])) //배열에 이미 같은 tweet이 있을 경우
                        {
                            tf = true;
                            ++word_n[k];
                        }
                        ++k;
                    }
                    if (tf==false) //처음 등장하는 tweet일 경우
                    {
                        word_t[index] = tweet[i][2];
                        word_n[index] = 1;
                        ++index;
                    }
                }

                ///////////////Bubble sorting/////////////////

                String temp1 = "";
                int temp2 = 0;

                for (int i = 0; i < word_n.length; ++i)
                {
                    for (int j = i; j < word_n.length; ++j)
                    {
                        if (word_n[i] < word_n[j])
                        {
                            temp1 = word_t[i];
                            word_t[i] = word_t[j];
                            word_t[j] = temp1;

                            temp2 = word_n[i];
                            word_n[i] = word_n[j];
                            word_n[j] = temp2;
                        }
                    }
                }

                for (int i = 0; i < 5; ++i)
                    System.out.println((i+1) + ": " + word_t[i] + " ( "+word_n[i]+" times )");
                //ranking: tweeted word ( ** times ) 
                System.out.println("");

            }

            ////////////////////////////////////////////////////////////////////////

            else if (choice == 3)
            {
                int val_t[] = new int [n_user];
                int user_tt[] = new int [n_user]; //# of tweet을 정렬하기 위해 똑같은 것을 하나 더 만든다.

                for (int i = 0; i < n_user; ++i)
                {
                    val_t[i] = i; // # of tweet의 index를 저장할 배열
                    user_tt[i] = user_t[i]; // copy # of tweet (user_t[i])
                }

                /////////////Bubble sorting////////////////

                int temp1 = 0;
                int temp2 = 0;

                for (int i = 0; i < n_user; ++i)
                {
                    for (int k = i; k < n_user; ++k)
                    {
                        if (user_tt[i] < user_tt[k])
                        {
                            temp1 = user_tt[i];
                            user_tt[i] = user_tt[k];
                            user_tt[k] = temp1;

                            temp2 = val_t[i];
                            val_t[i] = val_t[k];
                            val_t[k] = temp2;
                        }
                    }
                }

                for (int i = 0; i < 5; ++i)
                    System.out.println((i+1)+": "+user[val_t[i]][2] + " ( " + user_tt[i] + " times )");
                //2번과 마찬가지로 출력
                System.out.println("");
            }

            ////////////////////////////////////////////////////////////////

            else if (choice == 4)
            {
                String [] user_w = new String[1000];
                int index = 0;
                String tempstr = "";
                System.out.println("Type the words you want to search for: ");
                String word_s = key.next(); //검색할 단어 받는 부분
                boolean tf = false;
                int k = 0;

                for(int i = 0; i < n_tweet; ++i)
                {
                    if(tweet[i][2].equals(word_s))
                    {
                        tempstr = tempstr + tweet[i][0]; //아이디들을 일단 연속된 스트링으로 받는다
                        tempstr = tempstr + " ";
                    }
                }
                String [] temparr1 = tempstr.split(" "); // String을 배열에 담아줌
                String [] temparr2 = new String [temparr1.length]; // 중복되는 부분을 삭제하기 우한 배열

                for (int i = 0; i < temparr1.length; ++i)
                {
                    tf=false;
                    k=0;
                    while (k<temparr2.length)
                    {
                        if (temparr1[i].equals(temparr2[k])) // name이 중복되어 나타날 경우
                        {
                            tf = true;
                            break;
                        }
                        ++k;
                    }
                    if(tf==false)
                    {
                        temparr2[index] = temparr1[i]; // 처음 등장하는 name일 경우
                        ++index;
                    }
                }

                if(index==1 && temparr2[0].equals(""))
                    System.out.println("None");
                else
                {
                    System.out.println("Identification number - Screen name");
                    System.out.println("");
                    for (int i = 0; i < index; ++i)
                    {
                        for(int j = 0; j < n_user; ++j)
                        {
                            if(temparr2[i].equals(user[j][0]))
                                System.out.println(user[j][0] + " - " + user[j][2]);
                        }
                    }
                }
                System.out.println("");
            }
            else if (choice == 5)
            {

            }
            else if (choice == 6)
            {

            }
            else if (choice == 7)
            {

            }
            else if (choice == 8)
            {

            }
            else if (choice == 9)
            {

            }
            else if (choice == 99)
            {
                System.out.println("");
                break;
            }
            else
            {
                System.out.println("Wrong number, try again: ");
                System.out.println("");
            }
        }
    }
}