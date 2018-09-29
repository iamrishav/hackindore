<?php

require_once 'db_functions.php';
$db = new DB_Functions();


if(isset($_POST['phone']) && isset($_POST['isServerToken']))
{
    $userPhone = $_POST['phone'];
    $isServerToken = $_POST['isServerToken'];
   
    
        $token = $db->getToken($userPhone ,$isServerToken );
       
    
            
            echo json_encode($token);
            
        }
        

else {
    
    $response = "Required parameter (userPhone , isServerToken) is missing";
    echo json_encode($response);
}



?>