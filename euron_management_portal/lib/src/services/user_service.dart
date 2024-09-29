import 'dart:convert';
import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/data/models/user.dart';
import 'package:http/http.dart' as http;

class UserService {
  Future<User> getUser(int id) async {
    var endPoint = 'users/';
    final finalUrl = '$baseUrl$endPoint$id';

    final response = await http.get(Uri.parse(finalUrl));

    if (response.statusCode == 200) {
      return User.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to load User with Id: $id');
  }

Future<List<User>> getAllUsers() async {
  var endPoint = 'users';
  final finalUrl = '$baseUrl$endPoint';

  final response = await http.get(Uri.parse(finalUrl));

  if (response.statusCode == 200) {
    List<dynamic> jsonList = jsonDecode(response.body);
    return jsonList.map((json) => User.fromJson(json)).toList();
  }

  throw Exception('Failed to load users');
}


  Future<void> deleteUser(int id) async {
    var endPoint = 'users/';
    final finalUrl = '$baseUrl$endPoint$id';

    final response = await http.delete(Uri.parse(finalUrl));

    if (response.statusCode != 200) {
      throw Exception('Failed to delete User with Id: $id');
    }
  }

  Future<User> createUser(User user) async {
    var endPoint = 'users/';
    final finalUrl = '$baseUrl$endPoint';

    final response = await http.post(
      Uri.parse(finalUrl),
      headers: {
        'Content-Type': 'application/json',
      },
      body: user.toRawJsonWithoutId(),
    );

    if (response.statusCode == 201) {
      return User.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to create User');
  }

  Future<User> updateUser(User user) async {
    var endPoint = 'users/';
    final finalUrl = '$baseUrl$endPoint${user.id}';

    final response = await http.put(
      Uri.parse(finalUrl),
      headers: {
        'Content-Type': 'application/json',
      },
      body: user.toRawJson(),
    );

    if (response.statusCode == 200) {
      return User.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to update User with Id: ${user.id}');
  }
}
