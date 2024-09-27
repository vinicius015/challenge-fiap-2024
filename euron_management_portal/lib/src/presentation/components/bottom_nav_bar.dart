import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/pages/update_employees_data/update_employees_data_page.dart';
import 'package:euron_management_portal/src/presentation/pages/home_page.dart';
import 'package:euron_management_portal/src/presentation/pages/training/training_page.dart';
import 'package:flutter/material.dart';

class BottomNavBar extends StatefulWidget {
  const BottomNavBar(
      {super.key, required this.initialPage, required this.isAdmin});
  final int initialPage;
  final bool isAdmin;

  @override
  State<BottomNavBar> createState() => _BottomNavBarState();
}

class _BottomNavBarState extends State<BottomNavBar> {
  late int currentPage;
  late PageController pageController;

  @override
  void initState() {
    super.initState();
    currentPage = widget.initialPage;
    pageController = PageController(initialPage: currentPage);
  }

  void navigateToPage(int page) {
    pageController.animateToPage(page,
        duration: const Duration(milliseconds: 400), curve: Curves.ease);
    setState(() {
      currentPage = page;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        controller: pageController,
        onPageChanged: (page) {
          setState(() {
            currentPage = page;
          });
        },
        children: [
          HomePage(
            isAdmin: widget.isAdmin,
            navigateToTraining: () => navigateToPage(1),
            navigateToUpdateEmployeesData: widget.isAdmin ? () => navigateToPage(2) : null,
          ),
          TrainingPage(isAdmin: widget.isAdmin,),
          if (widget.isAdmin) const UpdateEmployeesDataPage(),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentPage,
        iconSize: 30,
        items: [
          const BottomNavigationBarItem(
              icon: Icon(Icons.home_rounded), label: 'Home'),
          const BottomNavigationBarItem(
              icon: Icon(Icons.school), label: 'Treinamentos'),
          if (widget.isAdmin)
            const BottomNavigationBarItem(
                icon: Icon(Icons.storage), label: 'Base de Funcionários'),
        ],
        unselectedItemColor: euronWhite,
        selectedItemColor: euronCyan,
        onTap: (page) {
          navigateToPage(page);
        },
      ),
    );
  }
}
