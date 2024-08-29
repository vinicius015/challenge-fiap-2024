import 'package:euron_management_portal/src/data/models/position.dart';

class User {
  final String _id;
  final String _password;
  final bool _isActive;
  final Position _position;
  final DateTime? _lastAccess;

  User(
      {required String id,
      required String password,
      required bool isActive,
      required Position position,
      DateTime? lastAccess
      })
      : _id = id,
        _password = password,
        _isActive = isActive,
        _position = position,
        _lastAccess = lastAccess;

  String get id => _id;

  String get password => _password;

  bool get isActive => _isActive;

  Position get position => _position;

  DateTime? get lastAccess => _lastAccess;
}


