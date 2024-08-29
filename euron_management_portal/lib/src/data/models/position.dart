import 'package:euron_management_portal/src/data/models/department.dart';

class Position {
  final String _name;
  final String _description;
  final Department _department;

  Position(
      {required String name,
      required String description,
      required Department department})
      : _name = name,
        _description = description,
        _department = department;

  String get name => _name;
  String get description => _description;
  Department get department => _department;
}
