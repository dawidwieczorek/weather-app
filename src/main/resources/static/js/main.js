        var config = {
            type: 'line',
            data: {
                datasets: [{
                    label: "",
                    backgroundColor: window.chartColors.red,
                    borderColor: window.chartColors.red,
                    data: [],
                    fill: false,
                }]
            },
            options: {
                legend: {
                   display: false,
                },
                responsive: true,
                title:{
                    display:true,
                    text:'temperature'
                },
                tooltips: {
                    mode: 'index',
                    intersect: false,
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                },
                scales: {
                    xAxes: [{
                        type: "time",
                        display: true,
                        time: {
                             unit: 'day'
                        },
                        scaleLabel: {
                            display: false
                        }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: ''
                        }
                    }]
                },
                pan: {
                    enabled: true,
                    mode: 'xy'
                },
                zoom: {
                     enabled: true,
                      mode: 'x',
                }
            }
        };


$(document).ready(function () {
    var ctx = document.getElementById("canvas").getContext("2d");
    window.myLine = new Chart(ctx, config);

    var initial = $('.dropdown').first();
    var initialType = initial.data('type');
    var inititalOption = initial.find('a.dropdown-option').first();

    getForecast(inititalOption, initialType);

    inititalOption.parent().addClass("active");

    $('.dropdown').each(function(){
        var type = $(this).data('type');
        $(this).find('a.dropdown-option').on("click", function(e){
             getForecast($(this), type);
        });
    });

});

function getForecast(option, type) {

    var query = {'id': option.data('id')};

    $.ajax({
        type: "POST",
        url: "/chart",
        contentType: "application/json",
        data: JSON.stringify(query),
        dataType: 'json',
        cache: false,
        success: function (forecast) {
            $('#feedback').hide();
            $('.dropdown-menu li').removeClass("active");
            config.options.title.text = forecast.city.name;
            config.options.scales.yAxes[0].scaleLabel.labelString = getXAxisLabel(type);

            config.data.datasets[0].label = type;
            config.data.datasets[0].data = getDataPoints(type, forecast.list);

            window.myLine.update();
            window.myLine.resetZoom();

            option.parent().addClass("active");
        },
        error: function (e) {
            $('#feedback').html(e.responseJSON.error);
            $('#feedback').show();
        }
    });

    function getDataPoints(type, array){
          var datapoints = [];
          for (var i = 0, len = array.length; i < len; i++) {
              var datapoint;
              switch(type) {
                  case "pressure":
                      datapoint = { x: moment(array[i].dt), y: array[i].main.pressure}
                      break;
                  case "humidity":
                      datapoint = { x: moment(array[i].dt), y: array[i].main.humidity}
                      break;
                  default:
                      datapoint = { x: moment(array[i].dt), y: array[i].main.temp}
              }
              datapoints.push(datapoint);
          }
          return datapoints;
    }


function getXAxisLabel(type){
              switch(type) {
                  case "pressure":
                      return "Pressure[hPa]";
                  case "humidity":
                      return "Humidity[%]";
                      break;
                  default:
                      return "Temp[°F]";
              }
    }
}