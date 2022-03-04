$(document).ready(function () {
    console.log("JS Ready for FLOOD FORECAST");
    floodForecast();
    changePageAndSize();
});

function changePageAndSize() {

    var url = window.location.href;
    console.log("CURRENT URL >>> "+ url);
    var pageSize = "pageSize=";

    if (url.indexOf('pageSize') > -1) {
        console.log("existing pagination" + window.location.search)
        url = url.replace(window.location.search, '');
    }

    if(url.indexOf('?date=') > -1) {
        console.log("date existing");
        url = url + "&" + pageSize;
    } else {
        console.log("date not existing");
        url = url + "?" + pageSize;
    }

    console.log("NEW URL >>> "+ url);

    $('#pageSizeSelectForecast').change(function(evt) {
        window.location.replace(url+ this.value + "&page=1");
    });
}

function floodForecast() {
     $('#forecastTable').SetEditable({
         $addButton: $('#but_addFF'),
         onEdit: function(arg) {
             var id = $(arg).attr('id');
             console.log("EDIT FLOOD FORECAST"+ id);
             var $cols = $("#forecastTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             data.intensity  = $cols.find('td').eq(2).text();
             data.sensitivityLevel  = $cols.find('td').eq(3).text();
             data.sensitivityDetail  = $cols.find('td').eq(4).text();
             var json = JSON.stringify(data);

             console.log("data for flood forecast [] \n"+json)

             var xhr = new XMLHttpRequest();
             xhr.open("POST", "/admin/forecast", true);
             xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
             xhr.send(json);

    //            $cols.find('td').each(function(i) {
    //                 console.log("val each == "+$(this).text())
    //                 console.log("\n ===="+i++);
    //            });

         },   //Called after edition
         onDelete: function(arg) {
             console.log("last row id" + $('tr').last().attr('id'))
             var id = $(arg).attr('id');
             console.log("DELETE FORECAST"+ id);
             var $cols = $("#intensityTable").find("#"+id);  //lee campos
             var data = {};
             data.date = $cols.find('td').eq(0).text();
             data.hour = $cols.find('td').eq(1).text();
             var json = JSON.stringify(data);
             console.log("delete for flood forecast >>> "+ json);
             var url = "/admin/forecast/"+data.date+"/"+data.hour;
             console.log("URL delete for flood forecast >>> "+ url);
             var xhttp = new XMLHttpRequest();
             xhttp.open("DELETE", url, true);
             xhttp.send();
         }
      });
}
