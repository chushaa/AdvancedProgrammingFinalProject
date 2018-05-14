package FinalProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.*;

/**
 * 
 * @author chushaa
 *
 */

public class FinalProjectController{

    @FXML
    private Button btn_Update;
    
    /**
     * Updates the Database fields with most current data scraped from website
     * @param event this is looking for the the event to occur
     * @throws IOException
     */
    
    @FXML
    void btn_UpdateHandle(ActionEvent event) throws IOException{
    	Document doc = Jsoup.connect("http://www.ygoscope.com/").get();
    	String secondSite = "http://yugiohtopdecks.com/card/";
    	
    	Elements table = doc.select("table");
    	Elements rows = table.get(0).select("tr");
    	
    	int rowCount = 0;
    	
    	for (Element row: rows) {
    		if (rowCount == 0){
    			rowCount++;
    		}else {
    			System.out.println(rowCount);
    			String name = row.select("th").get(1).text();
    			name = name.replace("'", "%27");
            	
    			//String siteName = name.replace("\\s", "+");
            	//siteName = siteName.replace("&", "%26");
            	//siteName = siteName.replace(",", "%2C");
            	//siteName = siteName.replace("'", "%27");
            	//Document secondDoc = Jsoup.connect(secondSite + siteName).get();
    			//String type =  row.select("td").get(0).text());
    			
    			String type = "Not Updated Yet";
            	
            	int rank = Integer.parseInt(row.select("th").get(0).text());
            	
            	String originalWinPercentage = row.select("th").get(6).text();
            	originalWinPercentage = originalWinPercentage.substring(0, 2);
            	int winPercentage = Integer.parseInt(originalWinPercentage);
            	
            	String originalWinLossRate = row.select("th").get(5).text();
            	if(!(Character.isDigit(originalWinLossRate.charAt(0)))) {
            		originalWinLossRate = originalWinLossRate.substring(1);
            	}
            	int winLossRate = Integer.parseInt(originalWinLossRate);
            	
            	int totalUses = Integer.parseInt(row.select("th").get(2).text());
            	
            	try {
            		connection = DriverManager.getConnection(connectionString);
    	        	String checkTable = "IF EXISTS(SELECT * FROM CardsAndDecks.dbo.Cards WHERE cardName = '" + name + "')"
    	        			+ "				UPDATE CardsAndDecks.dbo.Cards SET cardName = '" + name + 
    	        															  "',cardRank = " + rank +
    	        															  ",cardType = '" + type +
    	        															  "',cardWinPercentage = " + winPercentage +
    	        															  ",cardWinLossRate = " + winLossRate +
    	        															  ",cardTotalUses = " + totalUses +
    	        															  "WHERE cardName = '" + name +
    	        						"'ELSE INSERT INTO CardsAndDecks.dbo.Cards VALUES ('" + name + "'," + rank + ",'" + type + "'," + winPercentage + "," + winLossRate + "," + totalUses + ");";
    	        	statement = connection.createStatement();
    	        	int updateSet = statement.executeUpdate(checkTable);
    	        	String selectInitial = "SELECT TOP 200 * FROM CardsAndDecks.dbo.Cards ORDER BY cardRank";
    	        	resultSet = statement.executeQuery(selectInitial);
    		    	rowCount++;
            	}  
    		   catch (Exception e) {  
    		            e.printStackTrace();  
    		        }  
    		    finally {  
    		            if (connection != null) try { connection.close(); } catch(Exception e) {}
    		            if (statement != null) try { statement.close(); } catch(Exception e) {}  
    		            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
    		    } 
    		}
    	}
    }

    @FXML
    private TextField txt_CardName;
    
    //@FXML
    //void txt_nameUpdate(KeyEvent event) throws IOException{
    //	btn_ShowAll.setDisable(false);
    //	try {
	//    	connection = DriverManager.getConnection(connectionString);
	//    	String search = txt_CardName.getText();
	//    	String searchString = "SELECT * from CardsAndDecks.dbo.Cards WHERE cardName LIKE '%" + search +"%' ORDER BY cardRank;";
	//    	statement = connection.createStatement();
	//        resultSet = statement.executeQuery(searchString);
	//   }catch (Exception e) {  
	//            e.printStackTrace();  
	//        }  
	//    finally {  
	//            if (connection != null) try { connection.close(); } catch(Exception e) {}
	//            if (statement != null) try { statement.close(); } catch(Exception e) {}  
	//            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
	//    } 
    //}

    @FXML
    private TableView<Card> table_Main;

    @FXML
    private TableColumn<Card, String> table_Rank;

    @FXML
    private TableColumn<Card, String> table_Name;

    @FXML
    private TableColumn<Card, String> table_CardType;

    @FXML
    private TableColumn<Card, String> table_WinPercent;

    @FXML
    private TableColumn<Card, String> table_WinLossDifference;

    @FXML
    private TableColumn<Card, String> table_Uses;

    @FXML
    private Button btn_ShowAll;
    
    /**
     * Returns Table to full display and shows all records
     * @param event
     * @throws IOException
     */
    
    @FXML 
    void btn_ShowAllUpdate(ActionEvent event) throws IOException{
    	btn_ShowAll.setDisable(true);
    	txt_CardName.setText("");
    	try {
        	connection = DriverManager.getConnection(connectionString);
	    	String selectInitial = "SELECT TOP 200 * FROM CardsAndDecks.dbo.Cards ORDER BY cardRank";
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(selectInitial);
     	} catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            if (connection != null) try { connection.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}  
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
        } 
    }

    @FXML
    private Button btn_Search;
    
    /**
     * Searches for specific records in the database
     * @param event
     * @throws IOException
     */
    @FXML
    void btn_SearchHandeler(ActionEvent event) throws IOException{
    	btn_ShowAll.setDisable(false);
    	try {
	    	connection = DriverManager.getConnection(connectionString);
	    	String search = txt_CardName.getText();
	    	String searchString = "SELECT * FROM CardsAndDecks.dbo.Cards WHERE cardName LIKE '%" + search +"%' ORDER BY cardRank;";
	    	System.out.println(searchString);
	    	statement = connection.createStatement();
	        resultSet = statement.executeQuery(searchString);
	    }catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    finally {  
	            if (connection != null) try { connection.close(); } catch(Exception e) {}
	            if (statement != null) try { statement.close(); } catch(Exception e) {}  
	            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
	    } 
    }
    
    String connectionString =  
            "jdbc:sqlserver://carddatabase.c9dtraiq8lxy.us-east-2.rds.amazonaws.com:1433;"  
            + "database=CardsAndDecks;"  
            + "user=chushaa;"  
            + "password=V$Ja}Cak;";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	
	/**
	 * Initial events to happen on load.
	 * Displays the full database.
	 * Sets the table columns to their matching database fields.
	 */
    public void initialize() {
    	try {  
            connection = DriverManager.getConnection(connectionString);  
            
            System.out.println("Successful Connection");
            String selectInitial = "SELECT TOP 200 * FROM CardsAndDecks.dbo.Cards ORDER BY cardRank";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectInitial);
            
            List initialList = new ArrayList();
            
            while(resultSet.next()) {
            	String name = resultSet.getString("cardName");
            	int rank = resultSet.getInt("cardRank");
            	String type = resultSet.getString("cardType");
            	int winPercentage = resultSet.getInt("cardWinPercentage");
            	int winLossRate = resultSet.getInt("cardWinLossRate");
            	int totalUses = resultSet.getInt("cardTotalUses");
            	
            	Card cardInstance = new Card(name, rank, type, winPercentage, winLossRate, totalUses);
            	
            	initialList.add(cardInstance);
            }
            
            ObservableList<Card> observableCard = FXCollections.observableArrayList(initialList);
            
            PropertyValueFactory<Card, String> nameProperty = new PropertyValueFactory<Card, String>("name");
            PropertyValueFactory<Card, String> rankProperty = new PropertyValueFactory<Card, String>("rank");
            PropertyValueFactory<Card, String> typeProperty = new PropertyValueFactory<Card, String>("type");
            PropertyValueFactory<Card, String> winPercentageProperty = new PropertyValueFactory<Card, String>("winPercentage");
            PropertyValueFactory<Card, String> winLossRateProperty = new PropertyValueFactory<Card, String>("winLossRate");
            PropertyValueFactory<Card, String> totalUsesProperty = new PropertyValueFactory<Card, String>("totalUses");
            
            table_Rank.setCellValueFactory(rankProperty);
            table_Name.setCellValueFactory(nameProperty);
            table_CardType.setCellValueFactory(typeProperty);
            table_WinPercent.setCellValueFactory(winPercentageProperty);
            table_WinLossDifference.setCellValueFactory(winLossRateProperty);
            table_Uses.setCellValueFactory(totalUsesProperty);

            table_Main.setItems(observableCard);
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            if (connection != null) try { connection.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}  
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
        } 
    }
    
    
}
