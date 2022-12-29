package com.safesmart.safesmart.dto;

import java.util.ArrayList;
import java.util.List;

import com.safesmart.safesmart.model.OrderStatus;
import com.safesmart.safesmart.model.ValetDenominations;

public class ChangeRequestDto {

	private Long id;

	private Integer cents;

	private Integer nickels;

	private Integer dimes;

	private Integer quarters;

	private Integer den_1$;

	private Integer den_5$;

	private Integer den_10$;

	private Integer den_20$;

	private Integer den_50$;

	private Integer den_100$;

	private String type;

	private String orderStatus;

	private Long updatedByUser;

	private String updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCents() {
		return cents;
	}

	public void setCents(Integer cents) {
		this.cents = cents;
	}

	public Integer getNickels() {
		return nickels;
	}

	public void setNickels(Integer nickels) {
		this.nickels = nickels;
	}

	public Integer getDimes() {
		return dimes;
	}

	public void setDimes(Integer dimes) {
		this.dimes = dimes;
	}

	public Integer getQuarters() {
		return quarters;
	}

	public void setQuarters(Integer quarters) {
		this.quarters = quarters;
	}

	public Integer getDen_1$() {
		return den_1$;
	}

	public void setDen_1$(Integer den_1$) {
		this.den_1$ = den_1$;
	}

	public Integer getDen_5$() {
		return den_5$;
	}

	public void setDen_5$(Integer den_5$) {
		this.den_5$ = den_5$;
	}

	public Integer getDen_10$() {
		return den_10$;
	}

	public void setDen_10$(Integer den_10$) {
		this.den_10$ = den_10$;
	}

	public Integer getDen_20$() {
		return den_20$;
	}

	public void setDen_20$(Integer den_20$) {
		this.den_20$ = den_20$;
	}

	public Integer getDen_50$() {
		return den_50$;
	}

	public void setDen_50$(Integer den_50$) {
		this.den_50$ = den_50$;
	}

	public Integer getDen_100$() {
		return den_100$;
	}

	public void setDen_100$(Integer den_100$) {
		this.den_100$ = den_100$;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUpdatedByUser() {
		return updatedByUser;
	}

	public void setUpdatedByUser(Long updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	

//	
//	public List<ChangedCurrencyDto> difference(ChangeRequestDto c){
//	
//	List<ChangedCurrencyDto> list = new ArrayList<ChangedCurrencyDto>();
//	int changeNeeded = 0;
//	int depositedValue = 0;
//	
//	int diff = this.old_cents-c.old_cents;
//	if((diff)>0) {
//		list.add(new ChangedCurrencyDto("Pennies","$"+Integer.toString(diff),"0"));
//		changeNeeded+=diff;
//	}
//	if((diff)<0) {
//		depositedValue+=diff*-1;
//		list.add(new ChangedCurrencyDto("Pennies","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_nickels-c.old_nickels;
//	if((diff)>0) {
//		list.add(new ChangedCurrencyDto("Nickels","$"+Integer.toString(diff),"0"));
//		changeNeeded+=diff;
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("Nickels","0","$"+Integer.toString(diff*-1)));
//	}
//	diff= this.old_dimes-c.old_dimes;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("Dimes","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("Dimes","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_quarters -c.old_quarters;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("Quarters","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("Quarters","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_1$-c.old_den_1$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$1","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$1","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_5$-c.old_den_5$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$5","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$5","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_10$-c.old_den_10$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$10","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$10","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_20$-c.old_den_20$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$20","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$20","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_50$-c.old_den_50$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$50","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$50","0","$"+Integer.toString(diff*-1)));
//	}
//	diff = this.old_den_100$-c.old_den_100$;
//	if((diff)>0) {
//		changeNeeded+=diff;
//		list.add(new ChangedCurrencyDto("$100","$"+Integer.toString(diff),"0"));
//	}
//	if((diff)<0) {
//		depositedValue+=(diff*-1);
//		list.add(new ChangedCurrencyDto("$100","0","$"+Integer.toString(diff*-1)));
//	}
//	
//	list.add(new ChangedCurrencyDto("ALL" ,"$"+Integer.toString(changeNeeded),"$"+Integer.toString(depositedValue)));
//	 
//	return list;
//}
//
//	public List<ChangedCurrencyDto> compareCurrentBal(ValetDenominations c){
//		
//		List<ChangedCurrencyDto> list = new ArrayList<ChangedCurrencyDto>();
//		int changeNeeded = 0;
//		int depositedValue = 0;
//		
//		int diff = this.old_cents-c.getCents();
//		if((diff)>0) {
//			list.add(new ChangedCurrencyDto("Pennies","$"+Integer.toString(diff),"0"));
//			changeNeeded+=diff;
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("Pennies","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_nickels-c.getNickels();
//		if((diff)>0) {
//			list.add(new ChangedCurrencyDto("Nickels","$"+Integer.toString(diff),"0"));
//			changeNeeded+=diff;
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("Nickels","0","$"+Integer.toString(diff*-1)));
//		}
//		diff= this.old_dimes-c.getDimes();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("Dimes","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("Dimes","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_quarters -c.getQuarters();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("Quarters","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("Quarters","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_den_1$-c.getDen_1$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$1","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$1","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_den_5$-c.getDen_5$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$5","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) { 
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$5","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_den_10$-c.getDen_10$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$10","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$10","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_den_20$-c.getDen_20$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$20","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$20","0","$"+Integer.toString(diff*-1)));
//		}
//		diff = this.old_den_50$-c.getDen_50$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$50","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			changeNeeded+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$50","$"+Integer.toString(diff*-1),"0"));
//		}
//		diff = this.old_den_100$-c.getDen_100$();
//		if((diff)>0) {
//			changeNeeded+=diff;
//			list.add(new ChangedCurrencyDto("$100","$"+Integer.toString(diff),"0"));
//		}
//		if((diff)<0) {
//			depositedValue+=(diff*-1);
//			list.add(new ChangedCurrencyDto("$100","0","$"+Integer.toString(diff*-1)));
//		}
//		
//		list.add(new ChangedCurrencyDto("ALL" ,"$"+Integer.toString(changeNeeded),"$"+Integer.toString(depositedValue)));
//		
//		return list;
//	}
}
