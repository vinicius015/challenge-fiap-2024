import 'package:euron_management_portal/src/data/models/User.dart';

class Manual {
  final String _title;
  final String _subtitle;
  final String _content;
  final User _creator;
  final DateTime _createdDate;
  final User? _lastEditor;
  final DateTime? _editedDate;

  Manual({
    required String title,
    required String subtitle,
    required String content,
    required User creator,
    required DateTime createdDate,
    User? lastEditor,
    DateTime? editedDate,
  })  : _title = title,
        _subtitle = subtitle,
        _content = content,
        _creator = creator,
        _createdDate = createdDate,
        _lastEditor = lastEditor,
        _editedDate = editedDate;

  String get title => _title;
  String get subtitle => _subtitle;
  String get content => _content;
  User get creator => _creator;
  DateTime get createdDate => _createdDate;
  User? get lastEditor => _lastEditor;
  DateTime? get editedDate => _editedDate;
}
