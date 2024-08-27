import 'package:euron_management_portal/src/presentation/pages/login_page.dart';
import 'package:flutter/material.dart';
import 'package:euron_management_portal/src/config/globals.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Euron Management Portal',
      theme: ThemeData(
        useMaterial3: true,
      ).copyWith(
          scaffoldBackgroundColor: euronWhite,
          appBarTheme: const AppBarTheme(backgroundColor: euronDarkBlue),
          bottomNavigationBarTheme:
              const BottomNavigationBarThemeData(backgroundColor: euronDarkBlue)),
      home: const LoginPage(),
    );
  }
}
