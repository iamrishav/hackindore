<?php

require_once 'db_functions.php';
$db = new DB_Functions();



$response = array();

if(isset($_POST['menuid']))
{
    $menuid = $_POST['menuid'];
   
    
        $drinks = $db->getDrinkByMenuID($menuid);
       
    
            
            echo json_encode($drinks);
            
        }
        
      
    
    


else {
    
    $response["error_msg"] = "Required parameter (menuid) is missing";
    echo json_encode($response);
}



?>