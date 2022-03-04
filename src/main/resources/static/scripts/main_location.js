$(document).ready(function () {
    console.log("JS Ready For LOCATION");
    rainLocation();
});

function rainLocation() {
    $('#locationTable').SetEditable({
        $addButton: $('#but_addL'),
        onEdit: function(arg) {
            var id = $(arg).attr('id');
            console.log("EDIT YEY "+ id);
            var $cols = $("#locationTable").find("#"+id);  //lee campos
            var data = {};
            data.id = $cols.find('td').eq(0).text();
            data.name = $cols.find('td').eq(1).text();
            data.details  = $cols.find('td').eq(2).text();
            data.levelOne  = $cols.find('td').eq(3).text();
            data.levelTwo  = $cols.find('td').eq(4).text();
            data.levelThree  = $cols.find('td').eq(5).text();
            data.levelFour  = $cols.find('td').eq(6).text();
            var json = JSON.stringify(data);

            console.log("data for location [] \n"+json)

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/admin/location", true);
            xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
            xhr.send(json);

//            $cols.find('td').each(function(i) {
//                 console.log("val each == "+$(this).text())
//                 console.log("\n ===="+i++);
//            });

        },   //Called after edition
        onDelete: function(arg) {
            console.log("last row id" + $('tr').last().attr('id'))
            console.log("DELETE YEY");
            var id = "/admin/location/"+$(arg).attr('id');
            console.log("delete for location >>> "+ id);
            var xhttp = new XMLHttpRequest();
            xhttp.open("DELETE", id, true);
            xhttp.send();
        }, //Called after deletion
        onAdd: function(arg) { console.log("ADD YEY"); }
     });

}
