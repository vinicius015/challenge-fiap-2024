import 'dart:convert';

import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/data/models/position.dart';
import 'package:http/http.dart' as http;

class PositionService {

  Future<Position> getPosition(int id) async {
    var endPoint = 'positions/';
    final finalUrl = '$baseUrl$endPoint$id';

    final response = await http.get(Uri.parse(finalUrl));

    if (response.statusCode == 200) {
      return Position.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to load Position with Id: $id');
  }
}