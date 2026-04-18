<?php
require_once('koneksi.php');

$sql = "SELECT s.* FROM student s INNER JOIN my_friends f ON s.nrp = f.nrp ORDER BY f.created_at DESC";
$result = $con->query($sql);

$friends = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        array_push($friends, $row);
    }
}

echo json_encode($friends);
$con->close();
?>