<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Monitor CPU or Memory</title>

<!--
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
-->
<!--
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>
-->

<!-- angular includes -->
<script type="text/javascript" src="js/angular.min.js"></script>

<!-- angular-chart includes -->
<script src="js/chartjs/chart.js"></script>
<script src="js/angular-chart/angular-chart.css"></script>
<script src="js/angular-chart/angular-chart.js"></script>

<!-- My Javascript file -->
<script src= "js/MyApp.js"></script>

<!-- My CSS style file -->
<link rel="stylesheet" href="css/style.css">

</head>

<body>
    <div ng-app="myApp">
        <div id="systemInfoGraph"ng-controller="MyController">  
            <h1> Monitor CPU and memory of your own computer</h1>
            <label>CPU:
                <input type="checkbox" ng-model="checkboxModel.cpu" ng-true-value="1" ng-false-value="0">
            </label>
            <label>Memory:
                <input type="checkbox" ng-model="checkboxModel.memory" ng-true-value="1" ng-false-value="0">
            </label><br/>
          
            Time Interval: <input type="range" name="range" ng-model="interval" min="{{min}}"  max="{{max}}"> {{interval}}
            <div id="chart-canvas" style= "width:600px; height:300px;">
                <canvas id="updating-chart"></canvas>
            </div>
        </div>
    </div>
</body>
</html>