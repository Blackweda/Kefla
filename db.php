<?php

include_once 'config.php';

class DBConnect{
	
	private $connect;
	
	public function __construct(){
		
			$this->connect = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
			
			if(mysqli_connect_errno($this->connect)){
					
					echo "Failed to connect to MySQL: " . mysqli_connect_error();
			}
	}
	
	public function getDB(){
	
		return $this->connect;
	}	
}
?>