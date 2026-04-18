<?php
require_once('koneksi.php');

$sql = "DELETE FROM my_friends";

if ($con->query($sql) === TRUE) {
    echo json_encode(array("result" => "OK", "message" => "Successfully reset friends"));
} else {
    echo json_encode(array("result" => "ERROR", "message" => "Failed to reset friends"));
}

$con->close();
?>