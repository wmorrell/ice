ICE Documentation
=================

This is the source code of the `ICE documentation site <https://jbei.github.io/ice/>`__. We welcome
patches for any typos or errors you find.


Getting Started
_______________

You'll need to have Python installed.

1. Install the `Sphinx documentation generator <http://sphinx-doc.org/index.html>`__.

::
  
  pip install sphinx

2. Check out the ``ice-docs`` repository.

::

  git clone https://github.com/JBEI/ice-docs.git
    
3. Make your desired changes to ``index.html`` or to any of the ``.rst`` files referenced in ``master.rst``.

4. Build the changes with

::
  
  make html

5. If all goes well, you'll be able to view the updated documentation in the ``_build/html`` directory.
