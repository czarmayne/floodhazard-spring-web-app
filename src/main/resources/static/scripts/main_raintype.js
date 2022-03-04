$(document).ready(function () {
    console.log("JS Ready For RAIN TYPE");
    rainLocation();
});

function rainLocation() {
    $('#rainTypeTable').SetEditable({
        $addButton: $('#but_addRainType'),
        onEdit: function(arg) {
            var id = $(arg).attr('id');
            console.log("EDIT YEY "+ id);
            var $cols = $("#rainTypeTable").find("#"+id);  //lee campos
            var data = {};
            data.id = $cols.find('td').eq(0).text();
            data.minimumPrecipitationRate = $cols.find('td').eq(1).text();
            data.maximumPrecipitationRate  = $cols.find('td').eq(2).text();
            data.rainLevel  = $cols.find('td').eq(3).text();
            data.rainTypeDetail  = $cols.find('td').eq(4).text();
            var json = JSON.stringify(data);

            console.log("data for rainType [] \n"+json)

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/admin/rainType", true);
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
            var id = "/admin/rainType/"+$(arg).attr('id');
            console.log("delete for rainType >>> "+ id);
            var xhttp = new XMLHttpRequest();
            xhttp.open("DELETE", id, true);
            xhttp.send();
        }, //Called after deletion
        onAdd: function(arg) { console.log("ADD YEY"); }
     });

}
