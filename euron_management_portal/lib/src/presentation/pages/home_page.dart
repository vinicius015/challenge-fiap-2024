import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

import '../../config/globals.dart';

class HomePage extends StatelessWidget {
  final VoidCallback navigateToTraining;

  const HomePage(
      {super.key,
      required this.navigateToTraining});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(imagePath: 'assets/euron_logo.png'),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Text(
              'Bem-vindo(a) ao Euron Management Portal',
              style: TextStyle(fontSize: 23),
            ),
            const SizedBox(height: 25),
            const SizedBox(height: 25),
            ElevatedButton(
                onPressed: navigateToTraining,
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(150, 60),
                  backgroundColor: euronSoftPurple,
                  elevation: 0,
                  foregroundColor: euronWhite,
                ),
                child: const Text('Treinamentos',
                    style: TextStyle(fontSize: 20))),
          ],
        ),
      ),
    );
  }
}
