package com.example.finderapp.Model;

public class Model {

        String userName;
        String profilePic;
        String gmailId;
        String itemColor,itemLocation,itemName;
        String itemPlace,userId,userPhoneNumber,message;
        public Model()
        {

        }
        public Model(String userName,String profilePic,String gmailId,String itemColor,String itemLocation,String itemName,String itemPlace,String userId,String userPhoneNumber,String message)
        {
            this.userName=userName;
            this.profilePic=profilePic;
            this.gmailId=gmailId;
            this.itemColor=itemColor;
            this.itemLocation=itemLocation;
            this.itemName=itemName;
            this.itemPlace=itemPlace;
            this.userId=userId;
            this.userPhoneNumber=userPhoneNumber;
            this.message=message;


        }
        public String getUserName() { return userName;}

    public void setUserName(String userName) { this.userName=userName;}

    public String getProfilePic() { return profilePic;}

    public void setProfilePic(String profilePic) { this.profilePic=profilePic;}

    public String getGmailId() { return gmailId;}

    public void setGmailId(String gmailId) { this.gmailId=gmailId;}

    public String getItemColor() { return itemColor;}

    public void setItemColor(String itemColor) { this.itemColor=itemColor;}

    public String getItemLocation() { return itemLocation;}

    public void setItemLocation(String itemLocation) { this.itemLocation=itemLocation;}

    public String getItemName() { return itemName;}

    public void setItemName(String itemName) { this.itemName=itemName;}

    public String getItemPlace() { return itemPlace;}

    public void setItemPlace(String itemPlace) { this.itemPlace=itemPlace;}

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId=userId;}

    public String getUserPhoneNumber() { return userPhoneNumber;}

    public void setUserPhoneNumber(String userPhoneNumber) { this.userPhoneNumber=userPhoneNumber;}

    public String getMessage() { return message;}

    public void setMessage(String message) { this.message=message;}



}
