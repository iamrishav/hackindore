<?php

require_once 'db_functions.php';
$db = new DB_Functions();



$response = array();

if(isset($_POST['drinkid']))
{
    $drinkid = $_POST['drinkid'];
   
    
        $drinkimage = $db->getDrinkImageById($drinkid);
       
    
            
            echo json_encode($drinkimage);
            
        }
        
      
    
    


else {
    
    $response["error_msg"] = "Required parameter (drinkid) is missing";
    echo json_encode($response);
}



?>