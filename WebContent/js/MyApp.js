var app = angular.module('myApp', [])
.controller('MyController', MyController);

function MyController($scope, $http) {
        $scope.checkboxModel = {
                 cpu : '1',
                 memory : '1'
               };
        $scope.interval = 1;
        
        $scope.min = 1;
        $scope.max = 10;

        var ctx = document.getElementById('updating-chart').getContext('2d');

        var startingData = {
          labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
          datasets: [
              {
                  fillColor: "rgba(200,200,200,0.2)",
                  strokeColor: "rgba(200,200,200,1)",
                  pointColor: "rgba(200,200,200,1)",
                  pointStrokeColor: "#fff",
                  data: []
              },
              {
                  fillColor: "rgba(151,187,205,0.2)",
                  strokeColor: "rgba(151,187,205,1)",
                  pointColor: "rgba(151,187,205,1)",
                  pointStrokeColor: "#fff",
                  data: []
              }
          ]
        };

        var myLiveChart = new Chart(ctx);
        var optionsAnimation = {
                scaleOverride : true,
                scaleSteps : 10,
                scaleStepWidth : 10,
                scaleStartValue : 0,
                showTooltips: false
              }

        var optionsNoAnimation = {
                animation : false,
                scaleOverride : true,
                scaleSteps : 10,
                scaleStepWidth : 10,
                scaleStartValue : 0,
                showTooltips: false
              };
        myLiveChart.Line(startingData, optionsAnimation);
        
        setInterval(function() {
            $http({
                method : 'GET',
                url : 'sysInfoServlet',
                params: { includeCpu: $scope.checkboxModel.cpu, includeMem: $scope.checkboxModel.memory, interval: $scope.interval}
            }).success(function(data,status,headers,config){
                startingData.datasets[0].data = data.CPU;
                startingData.datasets[1].data = data.Mem;
                myLiveChart.Line(startingData, optionsNoAnimation);
            }).error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }, 1000);

};
