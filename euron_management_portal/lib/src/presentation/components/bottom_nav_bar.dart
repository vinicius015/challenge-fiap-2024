import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/pages/update_employees_data/update_employees_data_page.dart';
import 'package:euron_management_portal/src/presentation/pages/home_page.dart';
import 'package:euron_management_portal/src/presentation/pages/training/training_page.dart';
import 'package:flutter/material.dart';

class BottomNavBar extends StatefulWidget {
  const BottomNavBar({super.key});

  @override
  State<BottomNavBar> createState() => _BottomNavBarState();
}

class _BottomNavBarState extends State<BottomNavBar> {
  int currentPage = 0;
  PageController pageController = PageController(initialPage: 0);

  void navigateToPage(int page) {
    pageController.animateToPage(page,
        duration: const Duration(milliseconds: 400), curve: Curves.ease);
    setState(() {
      currentPage = page;
    });
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        body: PageView(
          controller: pageController,
          onPageChanged: (page) {
            setState(() {
              currentPage = page;
            });
          },
          children: [
            HomePage(
              navigateToTraining: () => navigateToPage(1),
              navigateToUpdateEmployeesData: () => navigateToPage(2),
            ),
            const TrainingPage(),
            const UpdateEmployeesDataPage(),
          ],
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: currentPage,
          iconSize: 30,
          items: const [
            BottomNavigationBarItem(
                icon: Icon(
                  Icons.home_rounded,
                ),
                label: 'Home'),
            BottomNavigationBarItem(
                icon: Icon(Icons.school), label: 'Treinamentos'),
            BottomNavigationBarItem(
                icon: Icon(Icons.storage), label: 'Base de Funcionários')
          ],
          unselectedItemColor: euronWhite,
          selectedItemColor: euronCyan,
          onTap: (page) {
            navigateToPage(page);
          },
        ),
      ),
    );
  }
}
