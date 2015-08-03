/* 
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
function showLogoutPane(){
    if ($("#profilePicture1").html() == ""){}
    
    else{
        if ($("#profilePictureURL").attr("src") == ""){
            $("#profilePicture2").show(500, function(){$("#profilePicture1").show(500);});          
        }

        else{
            $("#profilePicture3").show(500, function(){$("#profilePicture2").show(500, function(){$("#profilePicture1").show(500);});});
        }
    }
}    
