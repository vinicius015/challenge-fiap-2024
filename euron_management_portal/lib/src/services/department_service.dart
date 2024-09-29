import 'dart:convert';

import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/data/models/department.dart';
import 'package:http/http.dart' as http;

class DepartmentService {

  Future<Department> getDepartment(int id) async {
    var endPoint = 'departaments/';
    final finalUrl = '$baseUrl$endPoint$id';

    final response = await http.get(Uri.parse(finalUrl));

    if (response.statusCode == 200) {
      return Department.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to load Department with Id: $id');
  }
}