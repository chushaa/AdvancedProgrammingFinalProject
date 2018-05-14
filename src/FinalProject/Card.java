/**
 * 
 */
package FinalProject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;;

/**
 * @author chushaa
 *
 */
public class Card {
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty rank;
	private final SimpleStringProperty type;
	private final SimpleIntegerProperty winPercentage;
	private final SimpleIntegerProperty winLossRate;
	private final SimpleIntegerProperty totalUses;
	
	/**
	 * @param name is the Name of the Card
	 * @param rank is its current ranking
	 * @param type is the type of card it is
	 * @param winPercentage is the cards Win Percentage while being used
	 * @param winLossRate is the win vs. loss difference
	 * @param totalUses is the total number of uses recorded
	 */
	public Card(String name, int rank, String type, int winPercentage, int winLossRate, int totalUses) {
		this.name = new SimpleStringProperty(name);
		this.rank = new SimpleIntegerProperty(rank);
		this.type = new SimpleStringProperty(type);
		this.winPercentage = new SimpleIntegerProperty(winPercentage);
		this.winLossRate = new SimpleIntegerProperty(winLossRate);
		this.totalUses = new SimpleIntegerProperty(totalUses);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public int getRank() {
		return rank.get();
	}
	
	public void setRank(int rank) {
		this.rank.set(rank);
	}
	
	public String getType() {
		return type.get();
	}
	
	public void setType(String type) {
		this.type.set(type);
	}
	
	public int getWinPercentage() {
		return winPercentage.get();
	}
	
	public void setWinPercentage(int winPercentage) {
		this.winPercentage.set(winPercentage);
	}
	
	public int getWinLossRate() {
		return winLossRate.get();
	}
	
	public void setWinLossRate(int winLossRate) {
		this.winLossRate.set(winLossRate);
	}
	
	public int getTotalUses() {
		return totalUses.get();
	}
	
	public void setTotalUses(int totalUses) {
		this.totalUses.set(totalUses);
	}
}
